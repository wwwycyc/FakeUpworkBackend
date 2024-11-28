package com.wyc.server.mapper;

import com.wyc.pojo.Entity.FileEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface FileMapper {
    @Insert("insert into file (file_name, file_url, user_id) VALUES " +
            "(#{fileName}, #{fileUrl}, #{userId})")
    void saveFile(FileEntity file);

    @Select("select * from file where file_name=#{fileName}")
    FileEntity findFileByFileName(String fileName);
}
