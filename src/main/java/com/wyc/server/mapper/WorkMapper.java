package com.wyc.server.mapper;

import com.wyc.pojo.Entity.Part;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;
import com.wyc.pojo.Entity.WorkCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

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

    @Insert("INSERT INTO work (title, introduction, cover, content, poster, post_date, state) " +
            "VALUES (#{title}, #{introduction}, #{cover}, #{content}, #{poster}, #{postDate}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "workId", keyColumn = "work_id")
    void saveWork(WorkCard workCard);


    @Insert("INSERT INTO work_need (work_id, need_id) VALUES (#{workId}, #{needId})")
    void saveWork_Need(Integer workId, Integer needId);

    @Insert("INSERT INTO work_part (work_id, part_id) VALUES (#{workId}, #{partId})")
    void saveWork_Part(Integer workId, Integer partId);


    @Insert("INSERT INTO work_talent (work_id, talent_id) VALUES (#{workId}, #{talentId})")
    void saveWork_Talent(Integer workId, Integer talentId);

    @Select("SELECT * from work where work_id=#{workId}")
    Work getWorkByWorkId(Integer workId);
}
