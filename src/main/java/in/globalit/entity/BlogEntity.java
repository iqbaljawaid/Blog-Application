package in.globalit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE blog_entity SET deleted = true WHERE blog_id=?")
@Where(clause = "deleted=false")
/*
 * // By using the @Where annotation, we can't get the deleted product data in
 * case // we still want the deleted data to be accessible. // An example of
 * this is a user with administrator-level that has full access // and can view
 * the data that has been “deleted”. // To implement this, we shouldn't use
 * the @Where annotation but two different // annotations, // @FilterDef,
 * and @Filter. With these annotations we can dynamically add // conditions as
 * needed:
 * 
 * @FilterDef(name = "deletedBlogFilter", parameters = @ParamDef(name =
 * "isDeleted", type = "boolean"))
 * 
 * @Filter(name = "deletedBlogFilter", condition = "deleted = :isDeleted")
 */
public class BlogEntity {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;

	private String title;
	private String shortDesc;

	@Lob

	private String longDesc;
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;

	private boolean deleted = Boolean.FALSE;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@OneToMany(mappedBy = "blog")
	private List<CommentEntity> comments;

}
