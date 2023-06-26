package in.globalit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.globalit.binding.CommentBinder;
import in.globalit.constants.AppConstants;
import in.globalit.entity.BlogEntity;
import in.globalit.entity.CommentEntity;
import in.globalit.entity.UserEntity;
import in.globalit.repo.BlogRepository;
import in.globalit.repo.CommentRepository;
import in.globalit.repo.UserRepository;
import in.globalit.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private BlogRepository blogRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private HttpSession session;

	@Override
	public Boolean saveComment(CommentBinder binder) {

		CommentEntity commentEntity = new CommentEntity();

		BeanUtils.copyProperties(binder, commentEntity);
		Integer blogId = (Integer) session.getAttribute(AppConstants.SESSION_BLOG_ID);

		Optional<BlogEntity> findById = blogRepo.findById(blogId);
		if(findById.isPresent()) {
			BlogEntity blogEntity = findById.get();
			commentEntity.setBlog(blogEntity);
		}

		return commentRepo.save(commentEntity)!=null;

	}
	@Override
	public List<CommentEntity> showComments(Integer id) {
		BlogEntity blogEntity = new BlogEntity();
		Optional<UserEntity> findById = userRepo.findById(id);
		if(findById.isPresent()) {
			UserEntity userEntity = findById.get();
			blogEntity.setUser(userEntity);
		}
		
		Example<BlogEntity> of = Example.of(blogEntity);

		List<BlogEntity> allBlogs = blogRepo.findAll(of);
		
		List<CommentEntity> comments = new ArrayList<>();
        
        for (BlogEntity blog : allBlogs) {
            comments.addAll(blog.getComments());
        }
        return comments;
		
	}
	
	@Override
	public List<CommentEntity> detailsOfCommentors(Integer id) {
		CommentEntity commentEntity = new CommentEntity();
		Optional<BlogEntity> findById = blogRepo.findById(id);
		if(findById.isPresent()) {
			BlogEntity blogEntity = findById.get();
			commentEntity.setBlog(blogEntity);
			
		}
		Example<CommentEntity> example = Example.of(commentEntity);
		return commentRepo.findAll(example);
		
	}
	@Override
	public Boolean deleteComment(Integer id) {
		commentRepo.deleteById(id);
		return true;
	}

}
