package com.wyc.server.mapper;

import com.wyc.pojo.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    @Insert("insert into user ( username, password,name) " + "values"+"(#{username},#{password},#{name})")
    void add(User user);

    void updatepersonaldate(User user);

}
