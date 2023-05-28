package kimdinhhoc.student.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kimdinhhoc.student.dto.ResponseDTO;
import kimdinhhoc.student.service.JwtTokenService;

@RestController
public class LoginController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@PostMapping("/login")
	public ResponseDTO<String> login(
			@RequestParam("username") String username, 
			@RequestParam("password") String password) {
		//authen  fail throw exception above
	Authentication authentication =	authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
	
//	List<String> authorities = authentication.getAuthorities().stream()
//			.map(e -> e.getAuthority()).collect(Collectors.toList());
	
		//if login success, jwt -gen token (string)
		return ResponseDTO.<String>builder()
				.status(200)
				.data(jwtTokenService.createToken(username
//						, authorities
						))
				.build();
	}
}
