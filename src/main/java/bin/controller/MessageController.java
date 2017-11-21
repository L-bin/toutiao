package bin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bin.model.HostHolder;
import bin.model.Message;
import bin.model.User;
import bin.model.ViewObject;
import bin.service.MessageService;
import bin.service.UserService;
import bin.util.ToutiaoUtil;

@Controller
public class MessageController {

	@Autowired
	private UserService userService;

	@Autowired
	private HostHolder hostHolder;

	@Autowired
	private MessageService messageService;

	@RequestMapping(path = { "/msg/addMessage" }, method = { RequestMethod.POST })
	@ResponseBody
	public String add(@RequestParam("content") String content, @RequestParam("toId") int toId) {
		int fromId = hostHolder.getUser().getId();
		Message message = new Message();
		message.setFromId(fromId);
		message.setToId(toId);
		message.setCreatedDate(new Date());
		message.setContent(content);
		message.setConversationId(
				fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", fromId, toId));
		messageService.addMessage(message);
		return ToutiaoUtil.getJSONString(message.getId());
	}

	@RequestMapping(path = { "/msg/detail" }, method = { RequestMethod.GET })
	public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
		try {
			List<Message> messages = messageService.getConversationDetail(conversationId, 0, 10);
			List<ViewObject> vos = new ArrayList<ViewObject>();
			for (Message m : messages) {
				ViewObject vo = new ViewObject();
				User user = userService.getUser(m.getFromId());
				if (user == null) {
					continue;
				}
				vo.set("headUrl", user.getHeadUrl());
				vo.set("userName", user.getName());
				vo.set("message", m);
				vos.add(vo);
			}
			model.addAttribute("vos", vos);
		} catch (Exception e) {
			// 记录信息
			e.printStackTrace();
		}
		return "letterDetail";
	}

	@RequestMapping(path = { "/msg/list" }, method = { RequestMethod.GET })
	public String conversationList(Model model) {
		try {
			int localUserId = hostHolder.getUser().getId();
			List<Message> messages = messageService.getConversationList(localUserId, 0, 10);
			List<ViewObject> vos = new ArrayList<ViewObject>();
			for (Message m : messages) {
				ViewObject vo = new ViewObject();
				vo.set("conversation", m);
				// id放的是总数
				vo.set("totalCount", m.getId());
				vo.set("unreadCount", messageService.getUnreadCount(localUserId, m.getConversationId()));
				int targetId = m.getFromId() == localUserId ? m.getToId() : m.getFromId();
				User user = userService.getUser(targetId);
				vo.set("headUrl", user.getHeadUrl());
				vo.set("userName", user.getName());
				vo.set("targetId", targetId);
				vos.add(vo);
			}
			model.addAttribute("vos", vos);
		} catch (Exception e) {
			//
		}

		return "letter";
	}
}
