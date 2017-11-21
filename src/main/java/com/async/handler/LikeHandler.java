package com.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.async.EventHandler;
import com.async.EventModel;
import com.async.EventType;

import bin.model.Message;
import bin.model.User;
import bin.service.MessageService;
import bin.service.UserService;
@Component
public class LikeHandler implements EventHandler{

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	public void doHandler(EventModel model) {
		Message message=new Message();
		User user=userService.getUser(model.getActorId());
		message.setFromId(3);
		message.setToId(model.getEntityOwnerId());
		message.setCreatedDate(new Date());
		message.setContent("用户"+user.getName()+
				"赞了你的资讯：http://127.0.0.1:8080/news/"
				+String.valueOf(model.getEntityId()));
		message.setConversationId(String.format("%d_%d", 3, model.getEntityOwnerId()));
		messageService.addMessage(message);
	}

	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.LIKE);
	}

}
