package app.core.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exception.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class LoginManager {

	@Autowired
	private ApplicationContext ctx;

	public enum ClientType {
		Administrator, Company, Customer;
	}

	public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {

		if (clientType.equals(ClientType.Administrator)) {
			AdminService adminService = ctx.getBean(AdminService.class);
			if (adminService.login(email, password)) {
				return adminService;
			}
		}
		if (clientType.equals(ClientType.Company)) {
			CompanyService companyService = ctx.getBean(CompanyService.class);
			if (companyService.login(email, password)) {
				return companyService;
			}
		}
		if (clientType.equals(ClientType.Customer)) {
			CustomerService customerService = ctx.getBean(CustomerService.class);
			if (customerService.login(email, password)) {
				return customerService;
			}
		}
		throw new CouponSystemException("Login Field - client type not exists.");
	}

}