package com.wyc.server.service;

import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.WorkCard;
import com.wyc.pojo.VO.WorkDetailsVO;

import java.util.List;

public interface WorkService {
    List<String> getAllPartTypeName();

    List<String> getAllTalentTypeName();


    List<Talent> getAllTalentType();

    List<WorkCard> getWorkcardsByTalentIds(Integer[] talentIds);
    List<WorkCard> getOnesWorkcardsByTalentIds(String username, List<Integer> talentIds);

    void savePostWork(PostWorkDTO postWorkDTO);

    WorkDetailsVO getWorkDetailsByWorkId(Integer workId);

    String getPosterByWorkId(Integer workId);
}
