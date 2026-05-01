package org.example.new_boot_demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.new_boot_demo.mapper.UserMapper;
import org.example.new_boot_demo.service.UserService;
import org.example.new_boot_demo.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id","1");
        claims.put("username","张三");

        // 生成 jwt 的代码
        String token = JWT.create()
                .withClaim("user", claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12)) // 添加过期时间，new Date() 系统当前时间（毫秒）
                .sign(Algorithm.HMAC256("example"));

        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoi5byg5LiJIn0sImV4cCI6MTc3NzU4ODkwMn0." +
                "VCPYidEu3sA6RsiKwZ9V38Iba7jRySeMf7gNpDP6aPg";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("example")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token); // 验证 token， 生成一个解析后的 JWT 对象
        Map<String, Claim> claims = decodedJWT.getClaims();

        System.out.println(claims.get("user"));

    }


    @Test
    public void test1(){
        System.out.println(Md5Util.getMD5String("123456"));
    }
}
