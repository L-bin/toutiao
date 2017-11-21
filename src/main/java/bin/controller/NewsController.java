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

import bin.model.Comment;
import bin.model.EntityType;
import bin.model.HostHolder;
import bin.model.News;
import bin.model.ViewObject;
import bin.service.CommentService;
import bin.service.LikeService;
import bin.service.NewsSerivice;
import bin.service.UserService;
import bin.util.ToutiaoUtil;

@Controller
public class NewsController {
	
	
	@Autowired
	private NewsSerivice newsSerivice;
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private LikeService likeService;
	
	@RequestMapping(path={"/news/{newsId}"})
	public String newsDetail(@RequestParam("newsId") int newsId,Model model){
		News news=newsSerivice.getById(newsId);
		if(news!=null){
			
			int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
            if (localUserId != 0) {
                model.addAttribute("like", likeService.getLikeStatus(localUserId, news.getId(),EntityType.ENTITY_NEWS));
            } else {
                model.addAttribute("like", 0);
            }
			List<Comment> comments= commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);
			List<ViewObject> vos=new ArrayList<ViewObject>();
			for(Comment comment:comments){
				ViewObject vo=new ViewObject();
				vo.set("comment", comment);
				vo.set("user", userService.getUser(comment.getUserId()));
				vos.add(vo);
			}
			model.addAttribute("comments",vos);
		}
		model.addAttribute("news",news);
		model.addAttribute("owner",userService.getUser(news.getUserId()));
		return "detail";
	}
	
	@RequestMapping(path={"/user/addNews"},method={RequestMethod.POST})
	@ResponseBody
	public String addNews(@RequestParam("image") String image,
			@RequestParam("title") String title,
			@RequestParam("link") String link){
		try{
			News news=new News();
			if(hostHolder.getUser()!=null){
				news.setUserId(hostHolder.getUser().getId());
			}else{
				//匿名用户
				news.setUserId(3);
			}
			news.setImage(image);
			news.setLink(link);
			news.setTitle(title);
			news.setCreatedDate(new Date());
			newsSerivice.addNews(news);
			return ToutiaoUtil.getJSONString(0);
		}catch(Exception e){
			return ToutiaoUtil.getJSONString(1, "添加资讯失败");
		}
	}
	
	@RequestMapping(path={"/addComment"},method={RequestMethod.POST})
	public String addComment(@RequestParam("content") String content,
				@RequestParam("newsId") int newsId){
		try{
			Comment comment=new Comment();
			comment.setStatus(0);
			comment.setEntityId(newsId);
			comment.setContent(content);
			comment.setUserId(hostHolder.getUser().getId());
			comment.setCreatedDate(new Date());
			comment.setEntityType(EntityType.ENTITY_NEWS);
			commentService.addComment(comment);
			
			int count=commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
			newsSerivice.updateCommentCount(count, comment.getEntityId());
			
		}catch(Exception e){
			
		}
		return "redirect:/news/"+String.valueOf(newsId);
	}
}
