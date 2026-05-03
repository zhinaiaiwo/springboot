package org.example.new_boot_demo.controller;


import org.example.new_boot_demo.pojo.Result;
import org.example.new_boot_demo.utils.AliyunOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // 把文件内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename(); // 获取文件的原文件名
        // 保证文件的名字是唯一的，从而防止文件覆盖，  使用UUID
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

//        file.transferTo(new File("D:\\Project\\SpringBoot_Vue\\SaveFiles\\" + filename));
        String Url = AliyunOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(Url/*文件的 URL 地址*/);

    }
}
