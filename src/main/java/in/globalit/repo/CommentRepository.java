package in.globalit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.globalit.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
