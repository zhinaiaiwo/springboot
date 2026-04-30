package org.example.new_boot_demo;

import net.minidev.json.annotate.JsonIgnore;
import org.example.new_boot_demo.pojo.User;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserTest {

    @Test
    public void userTest(){
         int id = 1;//主键ID
         String username = "zhangsan";//用户名
         String password = "123456";//密码
         String nickname = "eqweqe";//昵称
         String email = "eqweqweda";//邮箱
         String userPic = "dawawdawda";//用户头像地址

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setUserPic(userPic);

        ObjectMapper Mapper = new ObjectMapper();
        String json = Mapper.writeValueAsString(user);

        System.out.println(json);

    }
}
