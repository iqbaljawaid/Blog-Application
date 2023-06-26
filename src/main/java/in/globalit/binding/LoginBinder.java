package in.globalit.binding;

import lombok.Data;

@Data
public class LoginBinder {
	
	private String email;
	//password field is only for updating generated password to new password in DB
	private String pzzwd;
	// As because my login and signup both are loading in home page thats why i have given names also
	private String firstName;
	private String lastName;

}
