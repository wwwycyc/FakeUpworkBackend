package com.wyc.server.controller;

import com.wyc.common.properties.JwtProperties;
import com.wyc.common.result.Result;
import com.wyc.common.utils.JwtUtil;
import com.wyc.pojo.DTO.UserLoginDTO;
import com.wyc.pojo.DTO.UserSignUpDTO;
import com.wyc.pojo.Entity.User;
import com.wyc.pojo.VO.ImageVO;
import com.wyc.pojo.VO.ResumeVO;
import com.wyc.pojo.VO.UserCardVO;
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
                .user(user)
                .token(token)
                .build();
        return Result.success("欢迎"+user.getName(),employeeLoginVO);
    }
    @PostMapping("/signup")
    public Result signup(@RequestBody UserSignUpDTO userSignUpDTO){
        log.info("用户注册：{}", userSignUpDTO);
        userService.signup(userSignUpDTO);
        return Result.success("注册成功");
    }

    @GetMapping("/getavatar")
    public Result getavatar(@RequestParam String username){
        log.info("获取{}的头像", username);
        User user=userService.getuserbyusername(username);
        ImageVO imageVO = ImageVO.builder()
                .image(user.getAvatar())
                .build();
        return Result.success(imageVO);
    }

    @PostMapping("/about/updatepersonaldate")
    public Result updatepersonaldate(@RequestBody User user){
        log.info("更新用户数据: {}", user);
        userService.updatepersonaldate(user);
        return Result.success("更新成功");
    }

    @GetMapping("/about/userCard")
    public Result getusercard(@RequestParam String username){
        log.info("获取{}的card相关数据", username);
        User user=userService.getuserbyusername(username);
        UserCardVO userCardVO=UserCardVO.builder()
                .name(user.getName())
                .cardImage(user.getCardImage())
                .star(user.getStar())
                .email(user.getEmail())
                .build();
        return Result.success(userCardVO);
    }

    @PostMapping("/about/saveResume")
    public Result updateResume(@RequestParam("content")String content,@RequestParam("username")String username){
        log.info("保存{}的resume相关数据", username);
        User user = userService.updateResume(content,username);
        ResumeVO resumeVO=ResumeVO.builder()
                .content(user.getResume())
                .build();
        return Result.success("保存成功",resumeVO);
    }
    @GetMapping("/about/getResume")
    public Result getResume(@RequestParam("username")String username){
        log.info("获取resume内容");
        User user = userService.getuserbyusername(username);
        String resume=user.getResume();
        return Result.success(resume);
    }
}
