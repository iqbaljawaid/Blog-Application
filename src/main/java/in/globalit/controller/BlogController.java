package in.globalit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.globalit.binding.BlogBinder;
import in.globalit.constants.AppConstants;
import in.globalit.entity.BlogEntity;
import in.globalit.props.AppProperties;
import in.globalit.repo.BlogRepository;
import in.globalit.service.BlogService;

@Controller
public class BlogController {
	
	@Autowired
	private AppProperties appProps;

	@Autowired
	private BlogService service;
	
	@Autowired
	private BlogRepository blogRepo;

	@Autowired
	private HttpSession session;

	@GetMapping("/dashboard")
	public String loadDashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");
		List<BlogEntity> blogs = service.viewBlogs(userId);
		model.addAttribute(AppConstants.LIST_OF_BLOGS, blogs);
		model.addAttribute(AppConstants.USER_ID_STR, userId);
		return "dashboard";
	}
	
	@GetMapping("/search-title")
	public String searchTitle(@RequestParam("title") String title,Model model) {
		
		List<BlogEntity> findByTitle = blogRepo.findByTitleContaining(title);
		model.addAttribute(AppConstants.LIST_OF_BLOGS, findByTitle);
		
		return "filter-dashboard";
	}

	@GetMapping("/create")
	public String loadCreatePost(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");
		model.addAttribute(AppConstants.USER_ID_STR, userId);
		model.addAttribute(AppConstants.CREATE_BLOG_MODEL, new BlogBinder());
		return AppConstants.CREATE_POST_VIEW_PAGE;
	}

	@PostMapping("/saveBlog")
	public String saveBlogPost(@ModelAttribute("createBlog") BlogBinder binder, Model model) {

		Boolean blog = service.createBlog(binder);
		if (Boolean.TRUE.equals(blog)) {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.BLOG_CREATION_SUCCESS_KEY));
		} else {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.BLOG_CREATION_FAILURE_KEY));
		}
		model.addAttribute(AppConstants.CREATE_BLOG_MODEL, new BlogBinder());
		return AppConstants.CREATE_POST_VIEW_PAGE;
	}

	@GetMapping("/show-blog")
	public String showMyBlog(@RequestParam("blogId") Integer blogId, Model model) {

		session.setAttribute(AppConstants.SESSION_BLOG_ID, blogId);

		return "redirect:/comment-details";

	}

	@GetMapping("/remove")
	public String deleteComment(@RequestParam("blogId") Integer blogId, Model model) {

		Boolean status = service.remove(blogId);
		if (Boolean.TRUE.equals(status)) {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.BLOG_DELETION_SUCCESS_KEY));
		} else {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.BLOG_DELETION_FAILURE_KEY));
		}
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID_STR);
		List<BlogEntity> blogs = service.viewBlogs(userId);
		model.addAttribute(AppConstants.LIST_OF_BLOGS, blogs);
		return "dashboard";

	}
	
	@GetMapping("/edit")
	public String updateEdit(@RequestParam("blogId") Integer blogId, Model model) {
		
		BlogBinder blogData = service.getBlogData(blogId);
		model.addAttribute(AppConstants.CREATE_BLOG_MODEL, blogData);
		return AppConstants.CREATE_POST_VIEW_PAGE;
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession mySession = request.getSession(false);
		if (mySession != null) {
			mySession.invalidate(); // invalidate the session if it exists
		}
		return "redirect:/"; // redirect to the login page
	}
	
}
