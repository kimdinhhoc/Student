package kimdinhhoc.student.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepartmentDTO {
	private int id;
	
	@NotBlank
	private String name;
	private Date createdAt;//java.util
	private Date updatedAt;
}
