package org.example.new_boot_demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @NotNull
    private Integer id;//主键ID
    private String username;//用户名

    @JsonIgnore // 让 springmvc 把当前对象转换成 JSON 字符串时忽略 password， 最终的 JSON 字符串中就会没有 password 属性了
//    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,16}$") // 5-16位，含大小写字母、数字、特殊字符
//    @Pattern(regexp = "^[A-Za-z0-9]{5,16}$")   // 5-16 位 A~Z， a~z，0~9
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\s{1,10}$")
    private String nickname;//昵称

    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
