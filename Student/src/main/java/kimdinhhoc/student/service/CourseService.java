package kimdinhhoc.student.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kimdinhhoc.student.dto.CourseDTO;
import kimdinhhoc.student.dto.PageDTO;
import kimdinhhoc.student.dto.SearchDTO;
import kimdinhhoc.student.entity.Course;
import kimdinhhoc.student.repository.CourseRepo;

public interface CourseService {

	void create(CourseDTO courseDTO);

	void update(CourseDTO courseDTO);

	void delete(int id);

	CourseDTO getById(int id);

	PageDTO<List<CourseDTO>> search(SearchDTO searchDTO);
}

@Service
class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseRepo courseRepo;

	@Override
	public void create(CourseDTO courseDTO) {
		Course course = new ModelMapper().map(courseDTO, Course.class);
		courseRepo.save(course);
	}

	@Override
	public void update(CourseDTO courseDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public CourseDTO getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	private CourseDTO convert(Course course) {
		return new ModelMapper().map(course, CourseDTO.class);
	}

	@Override
	public PageDTO<List<CourseDTO>> search(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending();

		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);

		if (searchDTO.getSize() == null)
			searchDTO.setSize(5);

		if (searchDTO.getKeyword() == null)
			searchDTO.setKeyword("");

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

		Page<Course> page = courseRepo.searchName("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<CourseDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		List<CourseDTO> courseDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());

		pageDTO.setData(courseDTOs);

		return pageDTO;
	}

}
