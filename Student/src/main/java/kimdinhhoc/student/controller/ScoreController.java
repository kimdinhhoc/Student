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

import kimdinhhoc.student.dto.AvgScoreByCourse;
import kimdinhhoc.student.dto.PageDTO;
import kimdinhhoc.student.dto.ResponseDTO;
import kimdinhhoc.student.dto.ScoreDTO;
import kimdinhhoc.student.dto.SearchScoreDTO;
import kimdinhhoc.student.service.ScoreService;

//ws: REST
@RestController
@RequestMapping("/score")
public class ScoreController {
	@Autowired // DI : dependency inject
	ScoreService scoreService;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid ScoreDTO scoreDTO) {
		scoreService.create(scoreDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@DeleteMapping("/") // ?id=1000
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		scoreService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/") // ?id=1000
	public ResponseDTO<ScoreDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<ScoreDTO>builder().status(200)
				.data(scoreService.getById(id)).build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> edit(@RequestBody @Valid ScoreDTO scoreDTO) {
		scoreService.update(scoreDTO);
		return ResponseDTO.<Void>builder().status(200).build();
	}

	@PostMapping("/search") // jackson
	public ResponseDTO<PageDTO<List<ScoreDTO>>> search(@RequestBody @Valid SearchScoreDTO searchDTO) {
		PageDTO<List<ScoreDTO>> pageDTO = scoreService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<ScoreDTO>>>builder().status(200).data(pageDTO).build();
	}

	@GetMapping("/avg-score-by-course")
	public ResponseDTO<List<AvgScoreByCourse>> avgScoreByCourse() {
		return ResponseDTO.<List<AvgScoreByCourse>>builder()
				.status(200)
				.data(scoreService.avgScoreByCourses()).build();
	}
}
