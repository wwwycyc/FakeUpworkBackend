package com.wyc.server.controller;

import com.wyc.common.result.Result;
import com.wyc.pojo.DTO.OnesWorkCardByTalentIdsDO;
import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.DTO.UpdateWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.WorkCard;
import com.wyc.pojo.VO.EditWorkVO;
import com.wyc.pojo.VO.PosterVO;
import com.wyc.pojo.VO.WorkDetailsVO;
import com.wyc.pojo.VO.WorkInitVO;
import com.wyc.server.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/work")
@Slf4j
public class WorkContorller {
    @Autowired
    private WorkService workService;

    /**
     * 获取全部的Part和Talent，用于初始化页面和多选框
     * @return
     */
    @GetMapping("/init")
    public Result workInit(){
        WorkInitVO workInitVO=new WorkInitVO();
        List<String> partlist= workService.getAllPartTypeName();
        List<Talent> talentlist= workService.getAllTalentType();
        workInitVO.setPartType(partlist);
        workInitVO.setTalentType(talentlist);
        return Result.success(workInitVO);
    }

    /**
     * 获得所有Part的Name
     * 好像没用到
     * @return
     */
    @GetMapping("/getPartType")
    public Result getAllPartTypeName(){
        List<String> list= workService.getAllPartTypeName();
        return Result.success(list);
    }

    /**
     * 获得所有Talent的Name
     * 好像没用到
     * @return
     */
    @GetMapping("/getTalentType")
    public Result getAllTalentTypeName(){
        List<String> list= workService.getAllTalentTypeName();
        return Result.success(list);
    }

    /**
     * 用于筛选，选中talentId的所有work
     * @param onesWorkCardByTalentIdsDO
     * @return
     */
    @PostMapping("/getOnesWorkcardsByTalentIds")
    public Result getWorkcardsByTalentIds(@RequestBody OnesWorkCardByTalentIdsDO onesWorkCardByTalentIdsDO) {

        String username = onesWorkCardByTalentIdsDO.getUsername();
        List<String> talentIdsStr = onesWorkCardByTalentIdsDO.getTalentIds(); // 接收到的是 List<String> 类型

//        if (talentIdsStr == null || talentIdsStr.isEmpty()) {
//            return Result.error("当前未选择 talentIds"); // 如果没有传 talentIds 参数，返回错误
//        }

        // 如果需要将 String 类型的 talentIds 转换为 Integer 类型，可以进行转换
        List<Integer> talentIds = talentIdsStr.stream()
                .map(Integer::parseInt)  // 转换每个元素为 Integer
                .collect(Collectors.toList());  // 收集为 List<Integer> 类型
        log.info("开始获取{}的标签为{}的工作卡片", username, talentIds);
        try {
            List<WorkCard> workCardCards = workService.getOnesWorkcardsByTalentIds(username, talentIds);
            return Result.success(workCardCards);
        } catch (Exception e) {
            log.error("获取工作卡片失败", e);
            return Result.error("服务器内部错误");
        }
    }

    /**
     * 发布Work
     * @param title
     * @param introduction
     * @param cover
     * @param content
     * @param poster
     * @param needId
     * @param partId
     * @param talentIds
     * @return
     */
    @PostMapping("/postWork")
    public Result savePostWork(
            @RequestParam("title") String title,
            @RequestParam(value = "introduction",required = false) String introduction,
            @RequestParam(value = "cover",required = false) String cover, // 处理封面文件
            @RequestParam("content") String content,
            @RequestParam("poster") String poster,
            @RequestParam("needId") int needId,
            @RequestParam("partId") int partId,
            @RequestParam("talentIds") List<Integer> talentIds
//            @RequestBody PostWorkDTO postWorkDTO
    ){

        PostWorkDTO postWorkDTO = PostWorkDTO.builder()
                .title(title)
                .introduction(introduction)
                .cover(cover)
                .content(content)
                .poster(poster)
                .needId(needId)
                .partId(partId)
                .talentIds(talentIds)
                .build();
        workService.savePostWork(postWorkDTO);
        return Result.success();
    }


    @GetMapping("/getWorkDetailsByWorkId")
    public Result getWorkByWorkId(@RequestParam("workId") Integer workId){
        log.info("获取workId为{}的内容",workId);
        WorkDetailsVO workDetailsVO = workService.getWorkDetailsByWorkId(workId);
        return Result.success(workDetailsVO);
    }

    @GetMapping("/getPosterByWorkId")
    public Result getPosterByWorkId(@RequestParam("workId") Integer workId){
        log.info("获取workId为{}的Poster",workId);
        PosterVO poster=PosterVO.builder()
                .username(workService.getPosterByWorkId(workId))
                .build();
        return Result.success(poster);
    }

    @DeleteMapping("/deleteByWorkId")
    public Result deleteByWorkId(@RequestParam("workId")Integer workId){
        log.info("删除Work,workId:{}",workId);
        workService.deleteByWorkId(workId);
        return Result.success("删除成功");
    }

    @DeleteMapping("/deleteByWorkIds")
    public Result deleteByWorkIds(@RequestParam("workIds")List<Integer> workIds){
        log.info("删除Work,workId:{}",workIds);
        workService.deleteByWorkIdList(workIds);
        return Result.success("删除成功");
    }

    @GetMapping("/getWorkAllByWorkId")
    public Result getWorkAllByWorkId(@RequestParam("workId")Integer workId){
        log.info("获取Work,workId:{}",workId);
        EditWorkVO editWorkVO = workService.getWorkAllByWorkId(workId);
        return Result.success(editWorkVO);
    }

    @PostMapping("/updateWork")
    public Result updateWork(
//            @RequestBody UpdateWorkDTO updateWorkDTO
            @RequestParam("workId") int workId,
            @RequestParam("title") String title,
            @RequestParam(value = "introduction",required = false) String introduction,
            @RequestParam(value = "cover",required = false) String cover, // 处理封面文件
            @RequestParam("content") String content,
            @RequestParam("needId") int needId,
            @RequestParam("partId") int partId,
            @RequestParam("talentIds") List<Integer> talentIds
    ){
        UpdateWorkDTO updateWorkDTO = UpdateWorkDTO.builder()
                .workId(workId)
                .title(title)
                .introduction(introduction)
                .cover(cover)
                .content(content)
                .needId(needId)
                .partId(partId)
                .talentIds(talentIds)
                .postDate(LocalDate.now())
                .build();
        workService.updateWork(updateWorkDTO);
        return Result.success("编辑成功");
    }
}
