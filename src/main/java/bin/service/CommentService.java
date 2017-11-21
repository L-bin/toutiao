package bin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bin.dao.CommentDAO;
import bin.model.Comment;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO commentDAO;
	
	public List<Comment> getCommentsByEntity(int entityId,int entityType){
		return commentDAO.selectByEntity(entityId, entityType);
	}
	
	public int addComment(Comment comment){
		return commentDAO.addComment(comment);
	}
	
	public int getCommentCount(int entityId,int entityType){
		return commentDAO.getCommentCount(entityId, entityType);
	}
}
