package com.wyc.server.service;

import com.wyc.pojo.DTO.SearchMessageDTO;
import com.wyc.pojo.Entity.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);

    List<Message> getMessageByUsername(String username);
    
    List<Message> getMessageSent(SearchMessageDTO searchMessageDTO);

    List<Message> getMessageReceive(SearchMessageDTO searchMessageDTO);

    List<Message> getMessageFromTo(SearchMessageDTO searchMessageDTO);
}
