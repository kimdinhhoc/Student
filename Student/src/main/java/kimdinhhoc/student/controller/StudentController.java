package kimdinhhoc.student.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kimdinhhoc.student.dto.ResponseDTO;
import kimdinhhoc.student.dto.StudentDTO;
import kimdinhhoc.student.service.StudentService;

//https://start.spring.io/
@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired // DI : dependency inject
	StudentService studentService;

	// gia su: khong upload file
	@PostMapping("/")
	public ResponseDTO<Void> newUser(
			@RequestBody @Valid StudentDTO studentDTO) {
		studentService.create(studentDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/") // ?id=1000
	public ResponseDTO<StudentDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<StudentDTO>builder().status(200)
				.data(studentService.getById(id)).build();
	}
	
	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		studentService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
//
//	@GetMapping("/download") // ?filename=abc.jpg
//	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception {
//		File file = new File("D:/" + filename);
////		resp.setContentType("image/png");
//		Files.copy(file.toPath(), resp.getOutputStream());
//	}
//

//
//	@GetMapping("/list")
//	public ResponseDTO<List<UserDTO>> list() {
//		List<UserDTO> userDTOs = userService.getAll();
//		return ResponseDTO.<List<UserDTO>>builder().status(200).data(userDTOs).build();
//	}
//
//	@PostMapping("/search") // copy lai user/list
//	public ResponseDTO<PageDTO<List<UserDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {
//		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);
//
//		return ResponseDTO.<PageDTO<List<UserDTO>>>builder().status(200).data(pageUser).build();
//	}
//
//	@PutMapping("/")
//	public ResponseDTO<UserDTO> edit(@ModelAttribute @Valid UserDTO userDTO) throws Exception {
//
//		if (!userDTO.getFile().isEmpty()) {
//			// ten file upload
//			String filename = userDTO.getFile().getOriginalFilename();
//			// luu lai file vao o cung may chu
//			File saveFile = new File("D:/" + filename);
//			userDTO.getFile().transferTo(saveFile);
//			// lay ten file luu xuong DATABASE
//			userDTO.setAvatarURL(filename);
//		}
//
//		userService.update(userDTO);
//		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
//	}
}
