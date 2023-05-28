package kimdinhhoc.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kimdinhhoc.student.entity.Student;

public interface StudentRepo 
	extends JpaRepository<Student, Integer> {
	
}
