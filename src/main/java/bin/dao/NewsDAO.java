package bin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bin.model.News;

public interface NewsDAO {
	int addNews(News news);
	
	List<News> selectByUserIdAndOffset(@Param("userId") int userId, 
			@Param("offset") int offset,@Param("limit") int limit);
	
	News getNewsById(int newsId);
	
	int updateCommentCount(@Param("commentCount") int commentCount,@Param("id") int id);
	
	int updateLikeCount(@Param("id") int id,@Param("count") int likeCount);
}
