package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.services.AdminService;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController  {

	@Autowired
	private SessionContext sessionContext;
	
	@PostMapping(path = "/add/company", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCompany(@RequestHeader String token, @RequestBody Company company) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			adminService.addCompany(company);
			return ResponseEntity.status(HttpStatus.OK).body("New company was added.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(path = "/update/company/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCompany(@RequestHeader String token, @RequestBody Company company, @PathVariable Integer companyId) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			adminService.updateCompany(company, companyId);
			return ResponseEntity.status(HttpStatus.OK).body("Company was updated.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping(path = "/delete/company/{companyID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCompany(@RequestHeader String token, @PathVariable Integer companyID) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			adminService.deleteCompany(companyID);
			return ResponseEntity.status(HttpStatus.OK).body("Company was deleted.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getAll/company", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllCompanies(@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllCompanies());
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getOne/company/{companyID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOneCompany(@RequestHeader String token, @PathVariable Integer companyID) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneCompany(companyID));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping(path = "/add/customer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCustomer(@RequestHeader String token, @RequestBody Customer customer) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			adminService.addCustomer(customer);
			return ResponseEntity.status(HttpStatus.OK).body("New custoemr was added.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(path = "/update/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCustomer(@RequestHeader String token, @RequestBody Customer customer, @PathVariable Integer customerId) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			adminService.updateCustomer(customer, customerId);
			return ResponseEntity.status(HttpStatus.OK).body("customer was updated.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping(path = "/delete/customer/{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCustomer(@RequestHeader String token, @PathVariable Integer customerID) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			adminService.deleteCustomer(customerID);
			return ResponseEntity.status(HttpStatus.OK).body("customer was deleted.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getAll/customer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllCustomers(@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllCustomers());
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getOne/customer/{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOneCustomer(@RequestHeader String token, @PathVariable Integer customerID) {
		try {
			Session s = sessionContext.getSession(token);
			AdminService adminService = (AdminService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneCustomer(customerID));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}