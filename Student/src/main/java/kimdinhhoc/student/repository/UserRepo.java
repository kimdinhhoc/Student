package kimdinhhoc.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kimdinhhoc.student.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE "
			+ "MONTH(u.birthdate) = :month AND DAY(u.birthdate) = :date ")
	List<User> searchByBirthday(
			@Param("date") int date,
			@Param("month") int month);
	
	// tim theo username
	// select user where username = ?
	User findByUsername(String username);

//	Optional<User> findByUsername(String username);

	// where name = :s springframework.data
	Page<User> findByName(String s, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.name LIKE :x ")
	Page<User> searchByName(@Param("x") String s, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.name LIKE :x " + "OR u.username LIKE :x")
	List<User> searchByNameAndUsername(@Param("x") String s);

	@Modifying
	@Query("DELETE FROM User u WHERE u.username = :x")
	void deleteUser(@Param("x") String username);

	// tu gen lenh xoa
	void deleteByUsername(String username);
}
