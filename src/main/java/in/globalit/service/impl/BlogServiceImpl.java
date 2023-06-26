package in.globalit.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.globalit.binding.BlogBinder;
import in.globalit.constants.AppConstants;
import in.globalit.entity.BlogEntity;
import in.globalit.entity.UserEntity;
import in.globalit.repo.BlogRepository;
import in.globalit.repo.UserRepository;
import in.globalit.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BlogRepository blogRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private HttpSession session;

	@Override
	public Boolean createBlog(BlogBinder binder) {

		BlogEntity blogEntity = new BlogEntity();
		BeanUtils.copyProperties(binder, blogEntity);
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID_STR);

		Optional<UserEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserEntity userEntity = findById.get();
			blogEntity.setUser(userEntity);
		}
		
		return blogRepo.save(blogEntity)!=null;
	}

	// to display in dashboard page
	@Override
	public List<BlogEntity> viewBlogs(Integer id) {
		BlogEntity blogEntity = new BlogEntity();
		Optional<UserEntity> findById = userRepo.findById(id);
		if(findById.isPresent()) {
			UserEntity userEntity = findById.get();
			blogEntity.setUser(userEntity);
		}
		
		Example<BlogEntity> example = Example.of(blogEntity);
		return blogRepo.findAll(example);
	}

	// to display content of blog in show-my-blog page
	@Override
	public BlogEntity showBlog(Integer id) {
		BlogEntity entity = new BlogEntity();
		entity.setBlogId(id);
		BlogEntity blogEntity = blogRepo.findById(entity.getBlogId()).get();
		if (blogEntity == null) {
			return null;
		}
		return blogEntity;

	}

	@Override
	public Boolean remove(Integer id) {
		blogRepo.deleteById(id);
		return true;
	}

	@Override
	public BlogBinder getBlogData(Integer blogId) {
		BlogBinder binder = new BlogBinder();
		Optional<BlogEntity> findById = blogRepo.findById(blogId);
		if (findById.isPresent()) {
			BlogEntity blogEntity = findById.get();
			BeanUtils.copyProperties(blogEntity, binder);
		}
		return binder;
	}

	// this method is used to show all records even the soft deleted one
	@Override
	public Iterable<BlogEntity> findAll(boolean isDeleted) {
		Session mySession = entityManager.unwrap(Session.class);
		Filter filter = mySession.enableFilter("deletedProductFilter");
		filter.setParameter("isDeleted", isDeleted);
		Iterable<BlogEntity> blogs = blogRepo.findAll();
		mySession.disableFilter("deletedProductFilter");
		return blogs;
	}

}
