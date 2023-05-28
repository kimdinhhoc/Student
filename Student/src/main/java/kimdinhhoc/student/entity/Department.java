package kimdinhhoc.student.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@CreatedDate //auto gen new date
	@Column(updatable = false)
	private Date createdAt;//java.util
	
	@LastModifiedDate
//	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	//khong bat buoc
	//one department to many users
	//mapby la ten thuoc tinh manytoone ben entity user
	@OneToMany(mappedBy = "department",
			fetch = FetchType.LAZY
//			,cascade = CascadeType.ALL
			)
	private List<User> users;
}
