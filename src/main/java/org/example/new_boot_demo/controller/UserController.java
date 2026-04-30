package org.example.new_boot_demo.controller;


import jakarta.validation.constraints.Pattern;
import org.example.new_boot_demo.pojo.Result;
import org.example.new_boot_demo.pojo.User;
import org.example.new_boot_demo.service.UserService;
import org.example.new_boot_demo.utils.JwtUtil;
import org.example.new_boot_demo.utils.Md5Util;
import org.example.new_boot_demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


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
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }
}
