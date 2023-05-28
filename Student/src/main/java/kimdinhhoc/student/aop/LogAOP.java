package kimdinhhoc.student.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import kimdinhhoc.student.dto.DepartmentDTO;
import lombok.extern.slf4j.Slf4j;

@Aspect 
@Component
@Slf4j
public class LogAOP {
	@Autowired
	CacheManager cacheManager;
	
	@AfterReturning(value = "execution(* jmaster.io.demo.service."
			+ "DepartmentService.getById(*))",
			returning = "returnValue" )
	public void getByDeparmentId(JoinPoint joinPoint,
			Object returnValue) {
		int id = (Integer) joinPoint.getArgs()[0];
		log.info("JOIN POINT: " + id);
		DepartmentDTO dto = (DepartmentDTO) returnValue;
		log.info(dto.getName());
		
	}
}
