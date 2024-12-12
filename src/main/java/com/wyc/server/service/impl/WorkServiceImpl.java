package com.wyc.server.service.impl;

import com.wyc.common.exception.BaseException;
import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.DTO.UpdateWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;
import com.wyc.pojo.Entity.WorkCard;
import com.wyc.pojo.VO.EditWorkVO;
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
        Work work =Work.builder()
                .title(postWorkDTO.getTitle())
                .introduction(postWorkDTO.getIntroduction())
                .cover(postWorkDTO.getCover())
                .content(postWorkDTO.getContent())
                .poster(postWorkDTO.getPoster())
                .accepter(null)
                .postDate(LocalDate.now())
                .acceptDate(null)
                .state(0)
                .needId(postWorkDTO.getNeedId())
                .partId(postWorkDTO.getPartId())
                .build();
        workMapper.saveWork(work);
        Integer workId = work.getWorkId();
        if (workId == null || workId == 0) {
            throw new BaseException("Work发布失败");
        }
        List<Integer> talentIds=postWorkDTO.getTalentIds();
        for(Integer talentId : talentIds){
            workMapper.saveWork_Talent(workId,talentId);
        }
    }

    @Override
    public WorkDetailsVO getWorkDetailsByWorkId(Integer workId) {
        Work work=workMapper.getWorkByWorkId(workId);
        if(work==null){
            throw new BaseException("当前Work已被删除或不存在");
        }
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
        if(work==null){
            throw new BaseException("当前Work已被删除或不存在");
        }
        String username=work.getPoster();
        return username;
    }

    @Override
    public void deleteByWorkId(Integer workId) {
        workMapper.deleteByWorkId(workId);
    }

    @Override
    public void deleteByWorkIdList(List<Integer> workIds) {
        for(Integer workId : workIds){
            workMapper.deleteByWorkId(workId);
        }
    }

    @Transactional
    @Override
    public EditWorkVO getWorkAllByWorkId(Integer workId) {
        Work work = workMapper.getWorkByWorkId(workId);
        if (work==null) throw new BaseException("无法找到当前Work");

        List<Integer> talentIds=workMapper.getTalentIdsByWorkId(workId);
        if (talentIds==null) throw new BaseException("无法找到当前Work的Talent");
        EditWorkVO editWorkVO= EditWorkVO.builder()
                .workId(work.getWorkId())
                .title(work.getTitle())
                .introduction(work.getIntroduction())
                .cover(work.getCover())
                .content(work.getContent())
                .needId(work.getNeedId())
                .partId(work.getPartId())
                .talentIds(talentIds)
                .build();
        return editWorkVO;
    }

    @Override
    public void updateWork(UpdateWorkDTO updateWorkDTO) {
        workMapper.updateWork(updateWorkDTO);
        workMapper.deletWork_TalentByworkId(updateWorkDTO.getWorkId());
        for(Integer talentId : updateWorkDTO.getTalentIds()){
            workMapper.saveWork_Talent(updateWorkDTO.getWorkId(), talentId);
        }
    }
}
