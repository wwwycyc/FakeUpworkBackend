package com.wyc.server.controller;

import com.wyc.common.properties.JwtProperties;
import com.wyc.common.result.Result;
import com.wyc.common.utils.JwtUtil;
import com.wyc.pojo.DTO.UserLoginDTO;
import com.wyc.pojo.DTO.UserSignUpDTO;
import com.wyc.pojo.Entity.User;
import com.wyc.pojo.VO.UserLoginVO;
import com.wyc.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fakeupwork")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        UserLoginVO employeeLoginVO = UserLoginVO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }
    @PostMapping("/signup")
    public Result signup(@RequestBody UserSignUpDTO userSignUpDTO){
        log.info("用户注册：{}", userSignUpDTO);
        userService.signup(userSignUpDTO);
        return Result.success("注册成功");
    }

    @PostMapping("/about/updatepersonaldate")
    public Result updatepersonaldate(@RequestBody User user){
        log.info("更新用户数据: {}", user);
        userService.updatepersonaldate(user);
        return Result.success("更新成功");
    }
}
