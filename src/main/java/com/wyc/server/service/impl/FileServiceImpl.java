package com.wyc.server.service.impl;

import com.wyc.pojo.Entity.FileEntity;
import com.wyc.server.mapper.FileMapper;
import com.wyc.server.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public FileEntity uploadFile(FileEntity file) {
        fileMapper.saveFile(file);
        return fileMapper.findFileByFileName(file.getFileName());
    }

    @Override
    public FileEntity findFileByFileName(String fileName) {
        return fileMapper.findFileByFileName(fileName);
    }
}
