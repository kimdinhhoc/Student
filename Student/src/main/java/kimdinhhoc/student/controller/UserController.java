package kimdinhhoc.student.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kimdinhhoc.student.dto.PageDTO;
import kimdinhhoc.student.dto.ResponseDTO;
import kimdinhhoc.student.dto.SearchDTO;
import kimdinhhoc.student.dto.UserDTO;
import kimdinhhoc.student.service.UserService;

//https://start.spring.io/
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired // DI : dependency inject
	UserService userService;

	@PostMapping("/")
	public ResponseDTO<Void> newUser(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException {
		if (!userDTO.getFile().isEmpty()) {
			// ten file upload
			String filename = userDTO.getFile().getOriginalFilename();
			// luu lai file vao o cung may chu
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);
			// lay ten file luu xuong DATABASE
			userDTO.setAvatarURL(filename);
		}
		userService.create(userDTO);

		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/download") // ?filename=abc.jpg
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception {
		File file = new File("D:/" + filename);
//		resp.setContentType("image/png");
		Files.copy(file.toPath(), resp.getOutputStream());
	}

	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		userService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/list")
	public ResponseDTO<List<UserDTO>> list() {
		List<UserDTO> userDTOs = userService.getAll();
		return ResponseDTO.<List<UserDTO>>builder().status(200).data(userDTOs).build();
	}

	@PostMapping("/search") // copy lai user/list
	public ResponseDTO<PageDTO<List<UserDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {
		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);

		return ResponseDTO.<PageDTO<List<UserDTO>>>builder().status(200).data(pageUser).build();
	}

	@PutMapping("/avatar")
	public ResponseDTO<UserDTO> avatar(@ModelAttribute @Valid UserDTO userDTO) throws Exception {
		if (!userDTO.getFile().isEmpty()) {
			// ten file upload
			String filename = userDTO.getFile().getOriginalFilename();
			// luu lai file vao o cung may chu
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);
			// lay ten file luu xuong DATABASE
			userDTO.setAvatarURL(filename);
		}

//		userService.updateAvatar(userDTO);
		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
	}
	
	@PutMapping("/")
	public ResponseDTO<UserDTO> edit(@ModelAttribute @Valid UserDTO userDTO) throws Exception {

		if (!userDTO.getFile().isEmpty()) {
			// ten file upload
			String filename = userDTO.getFile().getOriginalFilename();
			// luu lai file vao o cung may chu
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);
			// lay ten file luu xuong DATABASE
			userDTO.setAvatarURL(filename);
		}

		userService.update(userDTO);
		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
	}
}
