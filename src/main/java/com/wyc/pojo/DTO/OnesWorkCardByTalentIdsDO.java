package com.wyc.pojo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OnesWorkCardByTalentIdsDO {
    private String username;
    private List<String> talentIds;
}
