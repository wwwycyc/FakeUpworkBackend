package com.wyc.server.service;

import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;

import java.util.List;

public interface WorkService {
    List<String> getAllPartTypeName();

    List<String> getAllTalentTypeName();


    List<Talent> getAllTalentType();

    List<Work> getWorkcardsByTalentIds(Integer[] talentIds);
    List<Work> getOnesWorkcardsByTalentIds(String username,List<Integer> talentIds);

    void savePostWork(PostWorkDTO postWorkDTO);
}
