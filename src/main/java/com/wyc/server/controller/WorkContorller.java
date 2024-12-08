package com.wyc.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyc.common.result.Result;
import com.wyc.pojo.DTO.OnesWorkCardByTalentIdsDO;
import com.wyc.pojo.DTO.PostWorkDTO;
import com.wyc.pojo.Entity.Talent;
import com.wyc.pojo.Entity.Work;
import com.wyc.pojo.VO.WorkInitVO;
import com.wyc.server.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/work")
@Slf4j
public class WorkContorller {
    @Autowired
    private WorkService workService;

    @GetMapping("/init")
    public Result workInit(){
        WorkInitVO workInitVO=new WorkInitVO();
        List<String> partlist= workService.getAllPartTypeName();
        List<Talent> talentlist= workService.getAllTalentType();
        workInitVO.setPartType(partlist);
        workInitVO.setTalentType(talentlist);
        return Result.success(workInitVO);
    }
    @GetMapping("/getPartType")
    public Result getAllPartTypeName(){
        List<String> list= workService.getAllPartTypeName();
        return Result.success(list);
    }
    @GetMapping("/getTalentType")
    public Result getAllTalentTypeName(){
        List<String> list= workService.getAllTalentTypeName();
        return Result.success(list);
    }
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
            List<Work> workCards = workService.getOnesWorkcardsByTalentIds(username, talentIds);
            return Result.success(workCards);
        } catch (Exception e) {
            log.error("获取工作卡片失败", e);
            return Result.error("服务器内部错误");
        }
    }
    @PostMapping("/postWork")
    public Result savePostWork( @RequestParam("title") String title,
                                @RequestParam("introduction") String introduction,
                                @RequestParam(value = "cover",required = false) String cover, // 处理封面文件
                                @RequestParam("content") String content,
                                @RequestParam("poster") String poster,
                                @RequestParam("needId") int needId,
                                @RequestParam("partId") int partId,
                                @RequestParam("talentIds") List<Integer> talentIds
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
}
