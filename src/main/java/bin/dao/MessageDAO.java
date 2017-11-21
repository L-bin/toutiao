package bin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bin.model.Message;
public interface MessageDAO {
	int addMessage(Message message);
	
	List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
	
	List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);
	
	int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
	
	 int getConversationTotalCount(@Param("conversationId") String conversationId);
	
	//int updateMessage(@Param("fromId") int fromId,@Param("toId")int toId);
}
