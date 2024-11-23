package com.wyc.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCardVO implements Serializable {
    private String name;
    private byte[] cardImage;
    private float star;
    private String email;
}
