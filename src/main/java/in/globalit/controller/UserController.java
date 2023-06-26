package in.globalit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.globalit.binding.LoginBinder;
import in.globalit.binding.SignupBinder;
import in.globalit.constants.AppConstants;
import in.globalit.entity.BlogEntity;
import in.globalit.props.AppProperties;
import in.globalit.repo.BlogRepository;
import in.globalit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private AppProperties appProps;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private BlogRepository blogRepo;
	

	@GetMapping("/")
	public String loadIndex(Model model) {
		indexPageBinder(model);
		List<BlogEntity> viewBlogs = service.viewBlogs();
		model.addAttribute(AppConstants.LIST_OF_BLOGS, viewBlogs);
		return AppConstants.INDEX_VIEW_PAGE;
	}

	@PostMapping("/login")
	public String userLogin(@ModelAttribute("user") LoginBinder binder, Model model) {

		String status = service.employeeLogin(binder);
		if (status.contains(AppConstants.SUCCESS_STR)) {
			return "redirect:/dashboard";
		} else {
			model.addAttribute(AppConstants.UI_MSG, status);
			return AppConstants.INDEX_VIEW_PAGE;
		}
	}

	@PostMapping("/save")
	public String registerEmployee(@Validated @ModelAttribute("user") SignupBinder binder, Model model) {
		Boolean employeeSignup = service.employeeSignup(binder);
		if (Boolean.TRUE.equals(employeeSignup)) {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.SIGN_UP_SUCCESS_KEY));
		} else {
			model.addAttribute(AppConstants.UI_MSG, appProps.getMessages().get(AppConstants.SIGN_UP_FAILURE_KEY));
		}
		return AppConstants.INDEX_VIEW_PAGE;
		
	}
	
	@GetMapping("/search-index")
	public String searchTitle(@RequestParam("title") String title,Model model) {
		
		List<BlogEntity> findByTitle = blogRepo.findByTitleContaining(title);
		
		model.addAttribute(AppConstants.LIST_OF_BLOGS, findByTitle);
		indexPageBinder(model);
		
		return "index-filter";
	}

	private void indexPageBinder(Model model) {
		model.addAttribute(AppConstants.USER_BINDING_MODEL, new LoginBinder());
		model.addAttribute(AppConstants.USER_BINDING_MODEL, new SignupBinder());
	}

}
