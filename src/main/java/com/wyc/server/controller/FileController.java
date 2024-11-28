package com.wyc.server.controller;

import com.wyc.common.result.Result;
import com.wyc.pojo.Entity.FileEntity;
import com.wyc.server.service.FileService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.wyc.common.UrlConstant.ServerLocation;

@RestController
@RequestMapping("/uploads")
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
        Path directoryPath = Paths.get(uploadDir, username, location);
        Path filePath = directoryPath.resolve(newFileName);

        // 创建文件目录（如果目录不存在）
        try {
            Files.createDirectories(directoryPath); // 如果目录不存在，会自动创建
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory: " + directoryPath, e);
        }

        // 将文件保存到本地
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + filePath, e);
        }

        // 构建文件对象并保存到数据库
        FileEntity fileEntity = FileEntity.builder()
                .userId(username)
                .fileName(newFileName)
                .fileUrl(ServerLocation +uploadDir+"/"+username+"/"+location+"/"+ newFileName)  // 文件访问 URL
                .build();

        // 上传文件并保存
        FileEntity savedFile = fileService.uploadFile(fileEntity);

        // 返回文件的 URL
        return Result.success(savedFile.getFileUrl());
    }

    @GetMapping("/{username}/{location}/{fileName}")
    public void downloadFile(@PathVariable("username") String username,
                             @PathVariable("location") String location,
                             @PathVariable("fileName") String fileName,
                             HttpServletResponse response) throws IOException {
        log.info("开始加载文件");
        // 生成文件的存储路径
        Path filePath = Paths.get(uploadDir, username, location, fileName);

        // 检查文件是否存在
        File file = filePath.toFile();
        if (!file.exists()) {
            log.error("文件未找到: " + filePath);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("文件未找到！");
            return;
        }

        // 设置响应头信息，让浏览器下载文件
        response.setContentType(Files.probeContentType(filePath)); // 设置文件类型
        response.setContentLengthLong(file.length()); // 设置文件大小
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // 从文件中读取内容并将其写入响应流
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            // 写文件数据到响应流中
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            log.error("文件下载失败: " + filePath, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("文件下载失败！");
        }
    }

}
