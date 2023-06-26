package in.globalit.service;

import java.util.List;

import in.globalit.binding.LoginBinder;
import in.globalit.binding.SignupBinder;
import in.globalit.entity.BlogEntity;

public interface UserService {

	public Boolean employeeSignup(SignupBinder signup);
	public String employeeLogin(LoginBinder login);
	public List<BlogEntity> viewBlogs();
}
