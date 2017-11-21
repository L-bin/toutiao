package bin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bin.model.Comment;

public interface CommentDAO {
	int addComment(Comment comment);
	
	List<Comment> selectByEntity(@Param("entityId") int entityId,
			@Param("entityType") int entityType);
	
	int getCommentCount(@Param("entityId") int entityId,
			@Param("entityType") int entityType);
}
