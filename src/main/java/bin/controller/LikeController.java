package bin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.async.EventModel;
import com.async.EventProducer;
import com.async.EventType;

import bin.model.EntityType;
import bin.model.HostHolder;
import bin.model.News;
import bin.service.LikeService;
import bin.service.NewsSerivice;
import bin.util.ToutiaoUtil;

public class LikeController {
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private NewsSerivice newsSerivice;
	
	@Autowired
	private EventProducer eventProducer;
	
	@RequestMapping(path={"/like"},method={ RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public String like(@RequestParam("newsId") int newsId){
		long likeCount=likeService.like(hostHolder.getUser().getId(), newsId, EntityType.ENTITY_NEWS);
		newsSerivice.updateLikeCount(newsId, (int)likeCount);
		
		
		News news =newsSerivice.getById(newsId);
		eventProducer.fireEvent(new EventModel(EventType.LIKE).
				setActorId(hostHolder.getUser().getId()).setEntityId(newsId).
				setEntityType(EntityType.ENTITY_NEWS).setEntityOwnerId(news.getUserId()));
		return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
	}
	
	@RequestMapping(path={"/dislike"},method={ RequestMethod.POST,RequestMethod.GET })
	@ResponseBody
	public String disLike(@RequestParam("newsId") int newsId){
		long likeCount=likeService.disLike(hostHolder.getUser().getId(), newsId, EntityType.ENTITY_NEWS);
		newsSerivice.updateLikeCount(newsId, (int)likeCount);
		return ToutiaoUtil.getJSONString(0,String.valueOf(likeCount));
	}
}
