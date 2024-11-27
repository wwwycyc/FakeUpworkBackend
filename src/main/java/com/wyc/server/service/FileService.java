package com.wyc.server.service;

import com.wyc.pojo.Entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    FileEntity uploadFile(FileEntity file);
    FileEntity findFileByFileName(String fileName);
}
