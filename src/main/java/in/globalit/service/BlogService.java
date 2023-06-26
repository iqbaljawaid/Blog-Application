package in.globalit.service;

import java.util.List;

import in.globalit.binding.BlogBinder;
import in.globalit.entity.BlogEntity;

public interface BlogService {

	public Boolean createBlog(BlogBinder binder);

	public List<BlogEntity> viewBlogs(Integer userId);// for dashboard page to display table of blog detail

	public BlogEntity showBlog(Integer id);// to display content of blog from home page

	public Boolean remove(Integer id);
	
	public BlogBinder getBlogData(Integer blogId);
	
	public Iterable<BlogEntity> findAll(boolean isDeleted);// to show all records including the soft deleted one

}
