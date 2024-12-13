package com.wyc.server.service.impl;

import com.wyc.common.exception.BaseException;
import com.wyc.pojo.DTO.SearchMessageDTO;
import com.wyc.pojo.Entity.Message;
import com.wyc.server.mapper.MessageMapper;
import com.wyc.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Transactional
    @Override
    public void saveMessage(Message message) {
        String fromUsername=message.getFromUsername();
        if(fromUsername==null) throw new BaseException("未获取到发信人，请刷新后重试");
        String toUsername = message.getToUsername();
        if(toUsername==null) throw new BaseException("未获取到收信人，请刷新后重试");
        messageMapper.saveMessage(message);
        Integer messageId = message.getMessageId();
        if(messageId==null || messageId==0) throw new BaseException("发送失败，请稍后再试");
        messageMapper.saveUser_Message(fromUsername,messageId);
        messageMapper.saveUser_Message(toUsername,messageId);

    }

    @Override
    public List<Message> getMessageByUsername(String username) {
        return messageMapper.getMessageByUsername(username);
    }

    @Override
    public List<Message> getMessageSent(SearchMessageDTO searchMessageDTO) {
        return null;
    }

    @Override
    public List<Message> getMessageReceive(SearchMessageDTO searchMessageDTO) {
        return null;
    }

    @Override
    public List<Message> getMessageFromTo(SearchMessageDTO searchMessageDTO) {
        if(searchMessageDTO.getFromUsername()==null){
            throw new BaseException("未获取到发信人用户名，请稍后再试");
        }
        if(searchMessageDTO.getToUsername()==null){
            throw new BaseException("未获取到收信人用户名，请稍后再试");
        }
        List<Message> messageList = messageMapper.getMessageFromTo(searchMessageDTO);
        if(messageList==null){
            throw new BaseException("没有查到与该用户的通信历史");
        }
        return messageList;
    }
}
