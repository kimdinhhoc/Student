package kimdinhhoc.student.dto;//data tranform object

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDTO {

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
	private Date birthdate;
	
	//manytone
	private DepartmentDTO department;
	
	private List<String> roles;
	
	private int id;//0
	@Min(value = 1, message = "{min.msg}")
	private int age;
	@NotBlank(message = "{not.blank}")
	private String name;
	private String avatarURL;
	private String username;
	private String password;
	private String homeAddress;
	private String email;
	
	@JsonIgnore
	private MultipartFile file;
}
