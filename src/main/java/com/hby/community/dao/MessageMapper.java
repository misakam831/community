package com.hby.community.dao;

import com.hby.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    //查询当前用户的会话列表,返回最新的思想
    List<Message> selectConversations(int userId, int offset, int limit);

    //查询当前用户的会话数量
    int selectConversationCount(int userId);

    //查询每个会话的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询每个会话的私信数量
    int selectLetterCount(String conversationId);

    //查询未读的私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //新增消息
    int insertMessage(Message message);

    int updateStatus(List<Integer> ids, int status);
}
