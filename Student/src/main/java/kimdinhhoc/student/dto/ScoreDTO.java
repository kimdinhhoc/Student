package kimdinhhoc.student.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ScoreDTO {
	private int id;
	private double score;
	
	private CourseDTO course;
	private StudentDTO student;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
	private Date createdAt;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
	private Date updatedAt;
}
