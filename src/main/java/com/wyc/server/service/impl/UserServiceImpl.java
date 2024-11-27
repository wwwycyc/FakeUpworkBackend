package com.wyc.server.service.impl;

import com.wyc.pojo.DTO.UserLoginDTO;
import com.wyc.pojo.DTO.UserSignUpDTO;
import com.wyc.pojo.Entity.User;
import com.wyc.server.mapper.UserMapper;
import com.wyc.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUsername(username);
        if (user==null){
            throw new RuntimeException("账号不存在");
        }
        //密码比对
        //对前端传来的密码进行md5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new RuntimeException("密码错误");
        }

        //3、返回实体对象
        return user;
    }

    @Override
    public void signup(UserSignUpDTO userSignUpDTO) {
        String username = userSignUpDTO.getUsername();
        String password = userSignUpDTO.getPassword();
        String name = userSignUpDTO.getName();
        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setName(name);
        userMapper.add(user);
    }

    @Override
    public void updatepersonaldate(User user) {
        userMapper.updatepersonaldate(user);
    }

    @Override
    public User getuserbyusername(String username) {
        User user = userMapper.getByUsername(username);
        if (user==null){
            throw new RuntimeException("账号不存在");
        }
        return user;
    }

    @Override
    public User updateResume(String content, String username) {
        userMapper.updateResume(content,username);
        return userMapper.getByUsername(username);
    }
}
