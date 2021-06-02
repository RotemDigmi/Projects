package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.bean.LoginItem;
import app.core.client.LoginManager;
import app.core.client.LoginManager.ClientType;
import app.core.exception.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LogController {

	@Autowired
	private SessionContext sessionContext;
	@Autowired
	private LoginManager loginManager;

	@PostMapping("/login")
	public LoginItem login(@RequestParam String email, @RequestParam String password,
			@RequestParam ClientType clientType) {
		
		try {

			if (clientType.equals(ClientType.Administrator)) {
				AdminService adminService = (AdminService) loginManager.login(email, password, clientType);
				Session s = sessionContext.createSession();
				s.setAttributes("service", adminService);
				LoginItem loginItem = new LoginItem(s.token);
				return loginItem;
			}

			if (clientType.equals(ClientType.Company)) {
				CompanyService companyService = (CompanyService) loginManager.login(email, password, clientType);
				Session s = sessionContext.createSession();
				s.setAttributes("service", companyService);
				LoginItem loginItem = new LoginItem(s.token);
				return loginItem;
			}

			if (clientType.equals(ClientType.Customer)) {
				CustomerService customerService;
				customerService = (CustomerService) loginManager.login(email, password, clientType);
				Session s = sessionContext.createSession();
				s.setAttributes("service", customerService);
				LoginItem loginItem = new LoginItem(s.token);
				return loginItem;
			}
			
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		return null;
	}
	
	@DeleteMapping("/logOut")
	public ResponseEntity<?> logOut (@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			if(s != null) {
				sessionContext.invalidate(s);
				System.out.println("Session done, you are logged out.");
				return ResponseEntity.status(HttpStatus.OK).body("You are loged out.");
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Session NOT exists.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(e.getMessage());
		}
	}
}