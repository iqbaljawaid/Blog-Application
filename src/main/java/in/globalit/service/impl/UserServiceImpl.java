package in.globalit.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.globalit.binding.LoginBinder;
import in.globalit.binding.SignupBinder;
import in.globalit.entity.BlogEntity;
import in.globalit.entity.UserEntity;
import in.globalit.repo.BlogRepository;
import in.globalit.repo.UserRepository;
import in.globalit.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPzzwdEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BlogRepository blogRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public Boolean employeeSignup(SignupBinder signup) {
		
		UserEntity entity = new UserEntity();
		
		BeanUtils.copyProperties(signup, entity);
		entity.setPzzwd(bCryptPzzwdEncoder.encode(signup.getPzzwd()));
		if(userRepo.findByEmail(signup.getEmail())== null) {
			userRepo.save(entity);
		}
		return true;
	}

	@Override
	public String employeeLogin(LoginBinder login) {
		UserEntity entity = userRepo.findByEmail(login.getEmail());
		if (entity != null && bCryptPzzwdEncoder.matches(login.getPzzwd(), entity.getPzzwd())) {
			session.setAttribute("userId", entity.getUserId());

			return "success";
			
		}else {
			return "Invalid Credentials";
		}
		
		
	}
	
	@Override
	public List<BlogEntity> viewBlogs() {
		return blogRepo.findAll();
	}

}
