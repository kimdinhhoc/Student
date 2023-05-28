package kimdinhhoc.student.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kimdinhhoc.student.dto.CourseDTO;
import kimdinhhoc.student.dto.PageDTO;
import kimdinhhoc.student.dto.ResponseDTO;
import kimdinhhoc.student.dto.SearchDTO;
import kimdinhhoc.student.service.CourseService;

//ws: REST
@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired // DI : dependency inject
	CourseService courseService;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid CourseDTO courseDTO) {
		courseService.create(courseDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/") // ?id=1000
	public ResponseDTO<CourseDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<CourseDTO>builder().status(200).data(courseService.getById(id)).build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid CourseDTO courseDTO) {
		courseService.update(courseDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}

	@PostMapping("/search") // jackson
	public ResponseDTO<PageDTO<List<CourseDTO>>> search(
			@RequestBody @Valid SearchDTO searchDTO) {
		PageDTO<List<CourseDTO>> pageDTO = courseService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<CourseDTO>>>builder().status(200).data(pageDTO).build();
	}
}
