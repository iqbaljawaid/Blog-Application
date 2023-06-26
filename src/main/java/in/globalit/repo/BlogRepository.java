package in.globalit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.globalit.entity.BlogEntity;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
	
	public List<BlogEntity> findByTitleContaining(String title);

}
