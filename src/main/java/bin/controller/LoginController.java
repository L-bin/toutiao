package bin.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bin.service.UserService;
import bin.util.ToutiaoUtil;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(path={"/reg/"},method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String reg(Model model,@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value="rember",defaultValue="0") int rememberme,
			HttpServletResponse response){
		
		try{
			Map<String, Object> map=userService.register(username, password);
			if(map.containsKey("ticket")){
				Cookie cookie=new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if(rememberme > 0){
					cookie.setMaxAge(3600*24*5);
				}
				response.addCookie(cookie);
				return ToutiaoUtil.getJSONString(0,"×¢²á³É¹¦");
			}else{
				return ToutiaoUtil.getJSONString(1, map);
			}
		}catch(Exception e){
			return ToutiaoUtil.getJSONString(1, "×¢²áÊ§°Ü");
		}
	}
	
	@RequestMapping(path={"/login/"},method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String login(Model model,@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value="rember",defaultValue="0") int rememberme,
			HttpServletResponse response){
		
		try{
			Map<String, Object> map=userService.login(username, password);
			if(map.containsKey("ticket")){
				Cookie cookie=new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if(rememberme > 0){
					cookie.setMaxAge(3600*24*5);
				}
				response.addCookie(cookie);
				return ToutiaoUtil.getJSONString(0,"µÇÂ½³É¹¦");
			}else{
				return ToutiaoUtil.getJSONString(1, map);
			}
		}catch(Exception e){
			return ToutiaoUtil.getJSONString(1, "µÇÂ½Ê§°Ü");
		}
	}
	
	@RequestMapping(path={"/logout/"},method={RequestMethod.GET,RequestMethod.POST})
	public String logout(@CookieValue("ticket") String ticket){
		userService.logout(ticket);
		return "redirect:/index";
	}
}
