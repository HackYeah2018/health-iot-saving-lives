package au.gov.hack.health.iot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@PostMapping("/login")
	public ResponseEntity<String> login(HttpServletRequest request) {
		HttpSession s = request.getSession();
		
		String username = request.getParameter("username");
		logger.info("Received username [" + username + "]");
		
		s.setAttribute("username", username);
		
		return new ResponseEntity("login successful", HttpStatus.OK);
	}
	
}
