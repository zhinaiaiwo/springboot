package org.example.new_boot_demo.controller;


import jakarta.validation.constraints.Pattern;
import org.example.new_boot_demo.pojo.Result;
import org.example.new_boot_demo.pojo.User;
import org.example.new_boot_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
