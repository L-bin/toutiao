package bin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bin.dao.MessageDAO;
import bin.model.Message;

@Service
public class MessageService {
	
	@Autowired
	private MessageDAO messageDAO;
	
	public void addMessage(Message message){
		messageDAO.addMessage(message);
	}
	
	public List<Message> getConversationList(int userId, int offset, int limit){
		return messageDAO.getConversationList(userId, offset, limit);
	}
	
	public List<Message> getConversationDetail(String conversationId, int offset, int limit){
		List<Message> messages=messageDAO.getConversationDetail(conversationId, offset, limit);
		return messages;
	}
	
	public int getUnreadCount(int userId, String conversationId){
		return messageDAO.getConversationUnReadCount(userId, conversationId);
	}
}
