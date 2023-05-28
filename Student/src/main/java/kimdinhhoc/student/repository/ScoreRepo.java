package kimdinhhoc.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kimdinhhoc.student.dto.AvgScoreByCourse;
import kimdinhhoc.student.entity.Score;

public interface ScoreRepo extends JpaRepository<Score, Integer> {

	@Query("SELECT s FROM Score s WHERE s.student.userId = :sid ")
	Page<Score> searchByStudent(@Param("sid") int studentId, Pageable pageable);
	
	@Query("SELECT s FROM Score s WHERE s.course.id = :cid ")
	Page<Score> searchByCourse(@Param("cid") int courseId, Pageable pageable);
	
	//trung binh score theo course
	@Query("SELECT new jmaster.io.demo.dto.AvgScoreByCourse("
			+ "c.id, c.name, AVG(s.score)"
			+ ")"
			+ " FROM Score s JOIN s.course c GROUP BY c.id, c.name")
	List<AvgScoreByCourse> avgScoreByCourse();
	
	@Query("SELECT c.id, c.name, AVG(s.score)"
			+ " FROM Score s JOIN s.course c GROUP BY c.id, c.name")
	List<Object[]> avgScoreByCourse2();
}
