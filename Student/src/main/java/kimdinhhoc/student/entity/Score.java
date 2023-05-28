package kimdinhhoc.student.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Score extends TimeAuditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double score;
	
	@ManyToOne
	private Course course;//course:{"id":1}
	
	@ManyToOne
	private Student student;
	//san pham many to many mau sac (color)
	// many to many size (kich thuoc)
	//1 productcolor
	//1. productcolorsize
}
