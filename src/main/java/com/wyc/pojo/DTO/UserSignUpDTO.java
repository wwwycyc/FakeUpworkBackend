package com.wyc.pojo.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSignUpDTO implements Serializable {
    private String username;
    private String password;
    private String name;
}
