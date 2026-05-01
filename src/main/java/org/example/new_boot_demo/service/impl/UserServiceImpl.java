package org.example.new_boot_demo.service.impl;

import org.example.new_boot_demo.mapper.UserMapper;
import org.example.new_boot_demo.pojo.User;
import org.example.new_boot_demo.service.UserService;
import org.example.new_boot_demo.utils.Md5Util;
import org.example.new_boot_demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // password 加密， 使用 utils 例的 Md5Util （MD5）
        String md5String = Md5Util.getMD5String(password);

        // 添加
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePed(Md5Util.getMD5String(newPwd), id);
    }
}
