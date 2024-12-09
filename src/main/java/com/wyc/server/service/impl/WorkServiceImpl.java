package com.wyc.server.service.impl;

import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;
import com.wyc.pojo.Entity.WorkCard;
import com.wyc.pojo.VO.WorkDetailsVO;
import com.wyc.server.mapper.WorkMapper;
import com.wyc.server.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    private WorkMapper workMapper;
    @Override
    public List<String> getAllPartTypeName() {
        return workMapper.getAllPartTypeName();
    }

    @Override
    public List<String> getAllTalentTypeName() {
        return workMapper.getAllTalentTypeName();
    }

    @Override
    public List<Talent> getAllTalentType() {
        return workMapper.getAllTalentType();
    }

    @Override
    public List<WorkCard> getWorkcardsByTalentIds(Integer[] talentIds) {
        return workMapper.getWorkcardsByTalentIds(talentIds);
    }

    @Override
    public List<WorkCard> getOnesWorkcardsByTalentIds(String username, List<Integer> talentIds) {
        return workMapper.getOnesWorkcardsByTalentIds(username,talentIds);
    }

    @Transactional
    @Override
    public void savePostWork(PostWorkDTO postWorkDTO) {
        WorkCard workCard =new WorkCard();
        workCard.setTitle(postWorkDTO.getTitle());
        workCard.setIntroduction(postWorkDTO.getIntroduction());
        workCard.setCover(postWorkDTO.getCover());
        workCard.setContent(postWorkDTO.getContent());
        workCard.setPoster(postWorkDTO.getPoster());
        workCard.setPostDate(LocalDate.now());
        workCard.setState(0);
        workMapper.saveWork(workCard);
        Integer workId = workCard.getWorkId();
        if (workId == null || workId == 0) {
            throw new RuntimeException("Failed to insert work and obtain valid workId");
        }
        Integer needId= postWorkDTO.getNeedId();
        Integer partId= postWorkDTO.getPartId();
        List<Integer> talentIds=postWorkDTO.getTalentIds();

        workMapper.saveWork_Need(workId,needId);
        workMapper.saveWork_Part(workId,partId);
        for(Integer talentId : talentIds){
            workMapper.saveWork_Talent(workId,talentId);
        }
    }

    @Override
    public WorkDetailsVO getWorkDetailsByWorkId(Integer workId) {
        Work work=workMapper.getWorkByWorkId(workId);
        WorkDetailsVO workDetailsVO=WorkDetailsVO.builder()
                .title(work.getTitle())
                .content(work.getContent())
                .postDate(work.getPostDate())
                .build();
        return workDetailsVO;
    }

    @Override
    public String getPosterByWorkId(Integer workId) {
        Work work=workMapper.getWorkByWorkId(workId);
        String username=work.getPoster();
        return username;
    }
}
