package com.wyc.server.mapper;

import com.wyc.pojo.DTO.SearchMessageDTO;
import com.wyc.pojo.Entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Insert("insert into message (from_username,to_username,content,latest_date) values (#{fromUsername},#{toUsername},#{content},#{latestDate})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId", keyColumn = "message_id")
    void saveMessage(Message message);


    List<Message> getMessageByUsername(String username);

    List<Message> getMessageSent(SearchMessageDTO searchMessageDTO);
    List<Message> getMessageReceive(SearchMessageDTO searchMessageDTO);
    @Insert("insert into user_message(username, message_id) values (#{username},#{messageId})")
    void saveUser_Message(String username,Integer messageId);

    List<Message> getMessageFromTo(SearchMessageDTO searchMessageDTO);
}
