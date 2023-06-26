package in.globalit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.globalit.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByEmail(String email);

	public UserEntity findByEmailAndPzzwd(String email, String pzzwd);

}
