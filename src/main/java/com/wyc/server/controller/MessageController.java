package com.wyc.server.controller;


import com.wyc.common.result.Result;
import com.wyc.pojo.DTO.SearchMessageDTO;
import com.wyc.pojo.Entity.Message;
import com.wyc.server.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;
    @PostMapping("/saveMessage")
    public Result saveMessage(@RequestBody Message message){
        message.setLatestDate(LocalDate.now());
        messageService.saveMessage(message);
        return Result.success("发送成功");
    }

    @GetMapping("/getMessageByUsername")
    public Result getMessageByUsername(@RequestParam String username){
        log.info("获取{}的message中",username);
        List<Message> messageList = messageService.getMessageByUsername(username);
        return Result.success(messageList);
    }

    @GetMapping("/getMessageFromTo")
    public Result getMessageFromTo(@RequestBody SearchMessageDTO searchMessageDTO){
        List<Message> messageList = messageService.getMessageFromTo(searchMessageDTO);
        return Result.success(messageList);
    }
    @GetMapping("/getMessageSent")
    public Result getMessageSent(@RequestBody SearchMessageDTO searchMessageDTO){
        List<Message> messageList = messageService.getMessageSent(searchMessageDTO);
        return Result.success(messageList);
    }

    @GetMapping("/getMessageReceive")
    public Result getMessageReceive(@RequestBody SearchMessageDTO searchMessageDTO){
        List<Message> messageList = messageService.getMessageReceive(searchMessageDTO);
        return Result.success(messageList);
    }
}
