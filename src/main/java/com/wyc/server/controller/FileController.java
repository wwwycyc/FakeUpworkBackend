package com.wyc.server.controller;

import com.wyc.common.result.Result;
import com.wyc.pojo.Entity.FileEntity;
import com.wyc.server.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.wyc.common.UrlConstent.ServerLocation;

@RestController
@RequestMapping("/upload")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${spring.file.upload-dir}")
    private String uploadDir;

    // 文件上传接口
    @PostMapping("/resume")
    public Result uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("username") String username,
                             @RequestParam("location") String location) {

        log.info("上传{}的文件到{}",username,location);
        // 获取原始文件名和扩展名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = null;
        if (originalFilename != null) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 生成新的文件名
        String newFileName = UUID.randomUUID() + fileExtension;

        // 生成文件存储路径
        String filePath = uploadDir + "/" + username + "/" + location + "/" + newFileName;

        // 创建文件目录（如果目录不存在）
        Path directoryPath = Paths.get(uploadDir + "/" + username + "/" + location);
        try {
            Files.createDirectories(directoryPath); // 如果目录不存在，会自动创建
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory: " + directoryPath, e);
        }

        // 将文件保存到本地
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + filePath, e);
        }

        // 构建文件对象并保存到数据库
        FileEntity fileEntity = FileEntity.builder()
                .userId(username)
                .fileName(originalFilename)
                .fileUrl(ServerLocation + filePath)  // 文件访问 URL
                .build();

        // 上传文件并保存
        FileEntity savedFile = fileService.uploadFile(fileEntity);

        // 返回文件的 URL
        return Result.success(savedFile.getFileUrl());
    }
}
