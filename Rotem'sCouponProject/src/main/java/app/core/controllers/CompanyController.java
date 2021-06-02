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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exception.CouponSystemException;
import app.core.services.CompanyService;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/api/company")
public class CompanyController  {

	@Autowired
	private SessionContext sessionContext;
	
	@PostMapping(path = "/addCoupon", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCoupon(@RequestHeader String token, @RequestBody Coupon coupon) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			companyService.addCoupon(coupon);
			return ResponseEntity.status(HttpStatus.OK).body("New coupon was added.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(path = "/updateCoupon/{couponID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCoupon(@RequestHeader String token, @RequestBody Coupon coupon, @PathVariable Integer couponID) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			companyService.updateCoupon(coupon, couponID);
			return ResponseEntity.status(HttpStatus.OK).body("coupon was updated.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping(path = "/deleteCoupon/{couponID}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> deleteCoupon(@RequestHeader String token, @PathVariable Integer couponID) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			companyService.deleteCoupon(couponID);
			return ResponseEntity.status(HttpStatus.OK).body("coupon was deleted.");
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getCoupons/company", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCompanyCoupons(@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyCoupons());
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getCoupons/category", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCompanyCoupons(@RequestHeader String token, @RequestParam Category category) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyCoupons(category));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getCoupons/maxPrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCompanyCoupons(@RequestHeader String token, @RequestParam Double maxPrice) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyCoupons(maxPrice));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCompanyDetails(@RequestHeader String token) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyDetails());
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping(path = "/getCoupon/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCouponById(@RequestHeader String token, @PathVariable Integer couponId) {
		try {
			Session s = sessionContext.getSession(token);
			CompanyService companyService = (CompanyService) s.getAttributes("service");
			return ResponseEntity.status(HttpStatus.OK).body(companyService.getCouponById(couponId));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}