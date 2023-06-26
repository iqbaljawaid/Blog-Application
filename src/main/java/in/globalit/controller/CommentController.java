package in.globalit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.globalit.binding.CommentBinder;
import in.globalit.constants.AppConstants;
import in.globalit.entity.BlogEntity;
import in.globalit.entity.CommentEntity;
import in.globalit.props.AppProperties;
import in.globalit.service.BlogService;
import in.globalit.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private AppProperties appProps;

	@Autowired
	private CommentService service;

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	@PostMapping("/saveComment")
	public String saveComments(@ModelAttribute("comments") CommentBinder binder, Model model) {

		Boolean saveComment = service.saveComment(binder);
		if (Boolean.TRUE.equals(saveComment)) {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.BLOG_CREATION_SUCCESS_KEY));
		} else {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.BLOG_CREATION_FAILURE_KEY));
		}
		return "redirect:/comment-details";
	}

	@GetMapping("/comment-details")
	public String detailOfCommentors(Model model) {
		Integer blogId = (Integer) session.getAttribute(AppConstants.SESSION_BLOG_ID);
		BlogEntity showBlog = blogService.showBlog(blogId);// to show details of blog based on blog id
		model.addAttribute(AppConstants.COMMENT_BINDING_MODEL, new CommentBinder());
		model.addAttribute(AppConstants.DETAILS_OF_BLOG, showBlog);

		List<CommentEntity> commentors = service.detailsOfCommentors(blogId);// to show comment details based on blog id
		model.addAttribute(AppConstants.LIST_OF_COMMENTS, commentors);
		return "show-my-blog";
	}

	@GetMapping("/comment")
	public String viewComments(Model model) {

		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID_STR);

		List<CommentEntity> showComments = service.showComments(userId);
		model.addAttribute(AppConstants.LIST_OF_COMMENTS, showComments);

		return "viewComments";
	}

	@GetMapping("/delete")
	public String deleteComment(@RequestParam("commentId") Integer commentId, Model model) {

		Boolean status = service.deleteComment(commentId);
		if (Boolean.TRUE.equals(status)) {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.COMMENT_DELETION_SUCCESS_KEY));
		} else {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.COMMENT_DELETION_FAILURE_KEY));
		}
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID_STR);

		List<CommentEntity> showComments = service.showComments(userId);
		model.addAttribute(AppConstants.LIST_OF_COMMENTS, showComments);

		return "viewComments";
	}

}
