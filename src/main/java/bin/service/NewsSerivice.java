package bin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bin.dao.NewsDAO;
import bin.model.News;
@Service
public class NewsSerivice {

	@Autowired
	private NewsDAO newsDAO;
	
	public void addNews(News news){
		newsDAO.addNews(news);
	}
	
	
	public List<News> getLatestNews(int userId,int offset,int limit){
		return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
	}
	
	public News getById(int newsId){
		return newsDAO.getNewsById(newsId);
	}
	
	public int updateCommentCount(int commentCount,int id){
		return newsDAO.updateCommentCount(commentCount, id);
	}
	
	public int updateLikeCount(int id,int count){
		return newsDAO.updateLikeCount(id, count);
	}
}
