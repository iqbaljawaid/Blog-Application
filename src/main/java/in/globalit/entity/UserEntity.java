package in.globalit.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String pzzwd;
	
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<BlogEntity> blogs;
}
