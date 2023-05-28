package kimdinhhoc.student.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
	
	@GetMapping("/hello") //GET HTTP METHOD
	public String hi(HttpSession session) {
		//map url vao 1 ham, tra ve ten file view
		return "hi.html";
	}
	
}
