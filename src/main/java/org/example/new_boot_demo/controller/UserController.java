package org.example.new_boot_demo.controller;


import jakarta.validation.constraints.Pattern;
import org.example.new_boot_demo.pojo.Result;
import org.example.new_boot_demo.pojo.User;
import org.example.new_boot_demo.service.UserService;
import org.example.new_boot_demo.utils.JwtUtil;
import org.example.new_boot_demo.utils.Md5Util;
import org.example.new_boot_demo.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {

        // 手动校验 username 和 password 合法性
        /*if (username != null && username.length() >= 5 && username.length() <= 16 &&
        password != null && password.length() >= 5 && password.length() <= 16
        ) {*/
            // 查询用户
            User u = userService.findByUserName(username);
            if (u == null) {
                // 用户名没有被占用
                // 注册
                userService.register(username, password);
                return Result.success();
            } else {
                // 占用
                return Result.error("用户名已被占用");
            }
        /*}else {
            return Result.error("用户名或密码不合法");
        }*/

    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        // 判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }
        // 判断密码是否正确， 其中数据库存储的 password 为加密数据
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());

            String token = JwtUtil.genToken(claims);

            // 把 token 存储到 redis 里面
            stringRedisTemplate.opsForValue().set(token, token, 10, TimeUnit.DAYS);

            return Result.success(token);
        }

        return Result.error("用户名或密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {

        // 根据用户名查询用户
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUserName(username);

        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }


    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatepwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token){
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return  Result.error("缺少参数");
        }

        if (newPwd.length() > 16 || oldPwd.length() < 5 || rePwd.length() < 5 || oldPwd.length() > 16) {
            return Result.error("新密码不合法");
        }

        // 原密码是否正确
        // 调用 userService 根据根据用户名拿到密码， 再和 old_pwd 比对
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            System.out.println(user.getPassword());
            return Result.error("原密码不正确");
        }

        // newPwd 和 rePwd 是否一样
        if (!newPwd.equals(rePwd)){
            return Result.error("两次密码不一致");
        }

        userService.updatePwd(newPwd);
        // 删除 redis 中的对应的 token
        // 一下两种写法都可以
        stringRedisTemplate.opsForValue().getOperations().delete(token);
//        stringRedisTemplate.delete(token);

        return Result.success();

    }
}
