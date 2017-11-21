package bin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bin.dao.LoginTicketDAO;
import bin.dao.UserDAO;
import bin.model.LoginTicket;
import bin.model.User;
import bin.util.ToutiaoUtil;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LoginTicketDAO loginTicketDAO;
	
	public Map<String, Object> register(String username,String password){
		Map<String, Object> map=new HashMap<String, Object>();
		if(StringUtils.isBlank(username)){
			map.put("msgname", "用户名不能为空");
			return map;
		}
		if(StringUtils.isBlank(password)){
			map.put("msgpwd", "密码不能为空");
			return map;
		}
		
		User user=userDAO.selectByName(username);
		if(user!=null){
			map.put("msgname","用户名已被注册");
			return map;
		}
		
		
		//密码强度
		user=new User();
		user.setName(username);
		user.setSalt(UUID.randomUUID().toString().substring(0, 5));
		user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dm.png", new Random().nextInt(1000)));
		user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
		userDAO.addUser(user);
		
		//登陆
		String ticket=addLoginTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	
	public Map<String, Object> login(String username,String password){
		Map<String, Object> map=new HashMap<String, Object>();
		if(StringUtils.isBlank(username)){
			map.put("msgname", "用户名不能为空");
			return map;
		}
		if(StringUtils.isBlank(password)){
			map.put("msgpwd", "密码不能为空");
			return map;
		}
		
		User user=userDAO.selectByName(username);
		if(user==null){
			map.put("msgname","用户名不存在");
			return map;
		}
		
		if(!ToutiaoUtil.MD5(user.getPassword()+user.getSalt()).equals(user.getPassword())){
			map.put("msgpwd", "密码不正确");
			return map;
		}
		
		String ticket=addLoginTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	
	private String addLoginTicket(int userId){
		LoginTicket ticket=new LoginTicket();
		ticket.setUserId(userId);
		ticket.setStatus(0);
		Date date =new Date();
		date.setTime(date.getTime()+1000*3600*24);
		ticket.setExpired(date);
		ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDAO.addTicket(ticket);
		return ticket.getTicket();
	}
	
	public void logout(String ticket){
		loginTicketDAO.updateStatus(ticket, 1);
	}
	
	
	public User getUser(int id){
		return userDAO.selectById(id);
	}
}
