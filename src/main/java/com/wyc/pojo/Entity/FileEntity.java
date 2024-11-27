package com.wyc.pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    private Long id;

    private String fileName;

    private String fileUrl;

    private String userId;
}
