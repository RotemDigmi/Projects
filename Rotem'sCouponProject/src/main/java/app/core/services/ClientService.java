package app.core.services;


import app.core.exception.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;


public abstract class ClientService {
	
	protected CompanyRepository companyRepository;
	protected CustomerRepository customerRepository;
	protected CouponRepository couponRepository;
	
	public abstract boolean login(String email, String password) throws CouponSystemException;

}