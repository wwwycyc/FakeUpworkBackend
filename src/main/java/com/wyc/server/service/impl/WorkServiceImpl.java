package com.wyc.server.service.impl;

import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;
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
    public List<Work> getWorkcardsByTalentIds(Integer[] talentIds) {
        return workMapper.getWorkcardsByTalentIds(talentIds);
    }

    @Override
    public List<Work> getOnesWorkcardsByTalentIds(String username,List<Integer> talentIds) {
        return workMapper.getOnesWorkcardsByTalentIds(username,talentIds);
    }

    @Transactional
    @Override
    public void savePostWork(PostWorkDTO postWorkDTO) {
        Work work=new Work();
        work.setTitle(postWorkDTO.getTitle());
        work.setIntroduction(postWorkDTO.getIntroduction());
        work.setCover(postWorkDTO.getCover());
        work.setContent(postWorkDTO.getContent());
        work.setPoster(postWorkDTO.getPoster());
        work.setPostDate(LocalDate.now());
        work.setState(0);
        workMapper.saveWork(work);
        Integer workId = work.getWorkId();
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
}
