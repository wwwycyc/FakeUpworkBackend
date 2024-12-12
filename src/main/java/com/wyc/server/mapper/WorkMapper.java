package com.wyc.server.mapper;

import com.wyc.pojo.DTO.UpdateWorkDTO;
import com.wyc.pojo.Entity.Part;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;
import com.wyc.pojo.Entity.WorkCard;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface WorkMapper {
    @Select("select * from part")
    List<Part> getAllPartType();

    @Select("select * from talent")
    List<Talent> getAllTalentType();

    @Select("select part_name from part")
    List<String> getAllPartTypeName();

    @Select("select talent_name from talent")
    List<String> getAllTalentTypeName();

    List<WorkCard> getWorkcardsByTalentIds(Integer[] talentIds);
    List<WorkCard> getOnesWorkcardsByTalentIds(String username, List<Integer> talentIds);

    @Insert("INSERT INTO work (title, introduction, cover, content, poster, accepter,post_date, accept_date,state,part_id,need_id) " +
            "VALUES (#{title}, #{introduction}, #{cover}, #{content}, #{poster}, #{accepter},#{postDate},#{acceptDate}, #{state},#{partId},#{needId})")
    @Options(useGeneratedKeys = true, keyProperty = "workId", keyColumn = "work_id")
    void saveWork(Work work);

    @Insert("INSERT INTO work_talent (work_id, talent_id) VALUES (#{workId}, #{talentId})")
    void saveWork_Talent(Integer workId, Integer talentId);

    @Select("SELECT * from work where work_id=#{workId}")
    Work getWorkByWorkId(Integer workId);

    @Delete("DELETE from work where work_id=#{workId}")
    void deleteByWorkId(Integer workId);

    @Select("select talent_id from work_talent where work_id=#{workId}")
    List<Integer> getTalentIdsByWorkId(Integer workId);

    @Update("update work set title=#{title},introduction=#{introduction},cover=#{cover},content=#{content},post_date=#{postDate},part_id=#{partId},need_id=#{needId} where work_id=#{workId}")
    void updateWork(UpdateWorkDTO updateWorkDTO);

    @Delete("DELETE from work_talent where work_id=#{workId}")
    void deletWork_TalentByworkId(int workId);
}
