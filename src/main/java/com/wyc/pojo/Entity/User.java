package com.wyc.pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String username;
    private String password;
    private String name;
    private byte[] cardImage;//名片
    private String resume;
    private String email;
    private byte[] avatar;//头像
    private float star;
}
