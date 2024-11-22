package com.wyc.server.service;

import com.wyc.pojo.DTO.UserLoginDTO;
import com.wyc.pojo.DTO.UserSignUpDTO;
import com.wyc.pojo.Entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);
    void signup(UserSignUpDTO userSignUpDTO);

    void updatepersonaldate(User user);
}
