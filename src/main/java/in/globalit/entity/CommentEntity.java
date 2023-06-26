package in.globalit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer commentId;
	
	private String userName;
	private String email;
	@Lob
	private String comment;
	@CreationTimestamp
	private LocalDate createdDate;
	
	@ManyToOne
	@JoinColumn(name="blog_id")
	private BlogEntity blog;
}
