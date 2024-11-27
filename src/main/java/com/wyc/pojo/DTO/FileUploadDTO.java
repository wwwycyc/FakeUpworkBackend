package com.wyc.pojo.DTO;

import lombok.Data;

@Data
public class FileUploadDTO {
    private String url;

    public FileUploadDTO(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}