package org.example.new_boot_demo.service;

import org.example.new_boot_demo.pojo.User;

public interface UserService {

    // 根据用户名查询用户
    User findByUserName(String username);

    // 注册
    void register(String username, String password);
}
