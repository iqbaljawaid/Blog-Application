package in.globalit.service;

import java.util.List;

import in.globalit.binding.CommentBinder;
import in.globalit.entity.CommentEntity;

public interface CommentService {
	
	public Boolean saveComment(CommentBinder binder);
	
	public List<CommentEntity> detailsOfCommentors(Integer id);
	
	public List<CommentEntity> showComments(Integer id);
	
	public Boolean deleteComment(Integer id);
	
}
