package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.exception.CouponSystemException;
import app.core.entities.Coupon.Category;
import app.core.services.CustomerService;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private SessionContext sessionContext;
	
	
	@PostMapping(path = "/purchase/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> purchaseCoupon(@RequestHeader String token, @PathVariable Integer couponId) {
		try {
			Session s = sessionContext.getSession(token);
			CustomerService customerService = (CustomerService) s.getAttributes("service");
			customerService.purchaseCoupon(couponId);
			return ResponseEntity.status(HttpStatus.OK).body("Coupon successfully purchased.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getAllCoupons", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			CustomerService customerService = (CustomerService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerCoupons());
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getAll/category", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token, @RequestParam Category category) {
		try {
			Session s = sessionContext.getSession(token);
			CustomerService customerService = (CustomerService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerCoupons(category));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getAll/maxPrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCustomerCoupons(@RequestHeader String token, @RequestParam Double maxPrice) {
		try {
			Session s = sessionContext.getSession(token);
			CustomerService customerService = (CustomerService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerCoupons(maxPrice));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/get/details", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCustomerDetails(@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			CustomerService customerService = (CustomerService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerDetails());
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}