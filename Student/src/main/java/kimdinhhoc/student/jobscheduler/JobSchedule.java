package kimdinhhoc.student.jobscheduler;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kimdinhhoc.student.entity.User;
import kimdinhhoc.student.repository.UserRepo;
import kimdinhhoc.student.service.EmailService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
//	@Scheduled(fixedDelay = 60000)
	public void hello() {
		log.info("Hello ");
//		emailService.testEmail();
	}
	
	// GIAY - PHUT - GIO - NGAY - THANG - THU
//	@Scheduled(cron = "0 * * * * *")
	public void morning() {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		
		//thang 1 tuong ung 0
		int month = cal.get(Calendar.MONTH) + 1;
		
		List<User> users = userRepo.searchByBirthday(date, month);
		
		for (User u : users) {
			log.info("Happy Birthday " + u.getName());
//			emailService.sendBirthdayEmail(u.getEmail(), u.getName());
		}
		log.info("Good Morning");
	}
}
