package kimdinhhoc.student.service;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kimdinhhoc.student.dto.StudentDTO;
import kimdinhhoc.student.entity.Student;
import kimdinhhoc.student.repository.StudentRepo;

public interface StudentService {
	void create(StudentDTO studentDTO);

	void update(StudentDTO studentDTO);

	void delete(int id);

	StudentDTO getById(int id);
}

@Service
class StudentServiceImpl implements StudentService {
	@Autowired
	StudentRepo studentRepo;

	@Override
	@Transactional
	public void create(StudentDTO studentDTO) {
		// dung casecade
		Student student = new ModelMapper().map(studentDTO, Student.class);
		studentRepo.save(student);
	}

	@Override
	public StudentDTO getById(int id) {
		Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(student);
	}

	private StudentDTO convert(Student student) {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

//		modelMapper.getTypeMap(Student.class, StudentDTO.class)
//		.addMappings(mp -> {
//			mp.skip(StudentDTO::setStudentCode);
//		});

		return modelMapper.map(student, StudentDTO.class);
	}

	@Override
	@Transactional
	public void update(StudentDTO studentDTO) {
		Student student = studentRepo.findById(studentDTO.getUser().getId()).orElseThrow(NoResultException::new);

		student.getUser().setName(studentDTO.getUser().getName());

		studentRepo.save(student);
	}

	@Override
	@Transactional
	public void delete(int id) {
		studentRepo.deleteById(id);
	}

}
