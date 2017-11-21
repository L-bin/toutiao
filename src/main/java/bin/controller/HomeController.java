package bin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bin.model.EntityType;
import bin.model.HostHolder;
import bin.model.News;
import bin.model.ViewObject;
import bin.service.LikeService;
import bin.service.NewsSerivice;
import bin.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NewsSerivice newsSerivice;
	
	@Autowired
	private LikeService likeService;
	
	private List<ViewObject> getNews(int userId,int offset,int limit){
		List<News> newsList=newsSerivice.getLatestNews(userId, offset, limit);
		int localUserId=hostHolder.getUser()!=null? hostHolder.getUser().getId():0;
		List<ViewObject> vos=new ArrayList<ViewObject>();
		for(News news:newsList){
			ViewObject vo=new ViewObject();
			vo.set("news", news);
			vo.set("user", userService.getUser(news.getUserId()));
			if(localUserId!=0){
				vo.set("like", likeService.getLikeStatus(localUserId, news.getId(), EntityType.ENTITY_NEWS));
			}else{
				vo.set("like", 0);
			}
			vos.add(vo);
		}
		return vos;
	}
	
	@RequestMapping(path={"/","/index"},method={RequestMethod.GET,RequestMethod.POST})
	public String index(Model model){
		
		model.addAttribute("vos",getNews(0, 0, 10));
		
		return "home";
	}
	
	@RequestMapping(path={"/user/{userId}"},method={RequestMethod.GET,RequestMethod.POST})
	public String userIndex(Model model,@PathVariable("userId") int userId){
		
		model.addAttribute("vos",getNews(userId, 0, 10));
		return "home";
	}
	
}
