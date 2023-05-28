package kimdinhhoc.student.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kimdinhhoc.student.dto.DepartmentDTO;
import kimdinhhoc.student.dto.PageDTO;
import kimdinhhoc.student.dto.ResponseDTO;
import kimdinhhoc.student.dto.SearchDTO;
import kimdinhhoc.student.service.DepartmentService;

//ws: REST
@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired // DI : dependency inject
	DepartmentService departmentService;

	@PostMapping("/")
//	@ResponseBody
	public ResponseDTO<Void> create(
			@ModelAttribute @Valid DepartmentDTO departmentDTO) {
		departmentService.create(departmentDTO);
		return ResponseDTO.<Void>builder()
				.status(200)
				.msg("ok").build();
	}
	//neu day len dang JSON, dung RequestBody
	// json ko upload dc file
	@PostMapping("/json")
	public ResponseDTO<Void> createNewJSON(
			@RequestBody @Valid DepartmentDTO departmentDTO) {
		departmentService.create(departmentDTO);
		return ResponseDTO.<Void>builder().status(200)
				.msg("ok").build();
	}

	// HTTP STATUS CODE:
	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		return ResponseDTO.<Void>builder().status(200)
				.msg("ok").build();
	}

	@GetMapping("/") // ?id=1000
	@ResponseStatus(code = HttpStatus.OK)
//	@Secured({"ROLE_ADMIN","ROLE_SYSADMIN"})//ROLE_
//	@RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})
	@PreAuthorize("isAuthenticated()")
//	@PostAuthorize
	public ResponseDTO<DepartmentDTO> get(
			@RequestParam("id") int id) {
		return ResponseDTO.<DepartmentDTO>builder()
				.status(200)
				.data(departmentService.getById(id))
				.build();
	}

	@PutMapping("/")
	public ResponseDTO<DepartmentDTO> edit(@ModelAttribute @Valid DepartmentDTO departmentDTO) {
		departmentService.update(departmentDTO);
		return ResponseDTO.<DepartmentDTO>builder()
				.status(200)
				.data(departmentDTO)
				.build();
	}

	@PostMapping("/search") // jackson
	public ResponseDTO<PageDTO<List<DepartmentDTO>>> 
		search(@RequestBody @Valid SearchDTO searchDTO) {
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(searchDTO);
		
		return ResponseDTO.
				<PageDTO<List<DepartmentDTO>>>builder()
				.status(200)
				.data(pageDTO)
				.build();
	}
}
