package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class AdminService extends ClientService {

	private CompanyRepository companyRepository;
	private CustomerRepository customerRepository;
	private String email = "admin@mail.com";
	private String password = "admin123";

	public AdminService(CompanyRepository companyRepository, CustomerRepository customerRepository) {
		super();
		this.companyRepository = companyRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		if (this.email.equals(email) && this.password.equals(password)) {
			System.out.println("*** Welcome admin! ***");
			return true;
		} else {
			throw new CouponSystemException("*** Admin login failed.");
		}
	}

	public void addCompany(Company company) throws CouponSystemException {
		if (companyRepository.existsByNameOrEmail(company.getName(), company.getEmail())) {
			throw new CouponSystemException("*** You can't add a company, the name or email alredy exists.");
		}
		companyRepository.save(company);
		System.out.println("*** Company was added successfully.");
	}

	public void updateCompany(Company company, Integer companyId) throws CouponSystemException {
		Company currCompany = companyRepository.getById(companyId);
		if (currCompany != null) {
			currCompany.setEmail(company.getEmail());
			currCompany.setPassword(company.getPassword());
			System.out.println("*** Company with id " + currCompany.getId() + " was updated successfully.");
		} else {
			throw new CouponSystemException("*** Company with id " + companyId + " dosen't exists.");
		}
	}

	public void deleteCompany(Integer companyId) throws CouponSystemException {
		Company currCompany = companyRepository.getById(companyId);
		if (currCompany != null) {
		companyRepository.deleteById(companyId);
		System.out.println("*** Company with id " + companyId + " was deleted.");
		} else {
			throw new CouponSystemException("*** Company with id " + companyId + " dosen't exists.");
		}
	}

	public List<Company> getAllCompanies() throws CouponSystemException {
		List<Company> currCompanies = companyRepository.findAll();
		if (currCompanies != null) {
			System.out.println("*** All companies: ");
			return currCompanies;
		}else {
			throw new CouponSystemException("*** There are no existing companies.");
		}
	}

	public Company getOneCompany(Integer companyID) throws CouponSystemException {
		Company currCompany = companyRepository.getById(companyID);
		if (currCompany != null) {
			System.out.println("*** Company by ID: " + currCompany.getId());
			return currCompany;
		} else {
			throw new CouponSystemException("*** Company with id " + companyID + " dosen't exists.");
		}
	}

	public void addCustomer(Customer customer) throws CouponSystemException {
		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new CouponSystemException("*** You can't add a customer, the email alredy exists.");
		}
		customerRepository.save(customer);
		System.out.println("*** Customer was created successfully.");
	}

	public void updateCustomer(Customer customer, Integer customerId) throws CouponSystemException {
		Customer currCustomer = customerRepository.getById(customerId);
		if (currCustomer != null) {
			currCustomer.setFirstName(customer.getFirstName());
			currCustomer.setLastName(customer.getLastName());
			currCustomer.setEmail(customer.getEmail());
			currCustomer.setPassword(customer.getPassword());
			System.out.println("*** Customer with id " + customerId + " was updated successfully.");
		} else {
			throw new CouponSystemException("*** Customer with id " + customerId + " dosen't exists.");
		}
	}

	public void deleteCustomer(int customerId) throws CouponSystemException {
		Customer currCustomer = customerRepository.getById(customerId);
		if (currCustomer != null) {
		customerRepository.deleteById(customerId);
		System.out.println("*** Customer with id " + customerId + " was deleted.");
		}else {
			throw new CouponSystemException("*** Customer with id " + customerId + " dosen't exists.");
		}
	}

	public List<Customer> getAllCustomers() throws CouponSystemException {
		List<Customer> currCustomers = customerRepository.findAll();
		if (currCustomers != null) {
			System.out.println("*** All customers: ");
			return currCustomers;
		}else {
			throw new CouponSystemException("*** There are no existing customers.");
		}
	}

	public Customer getOneCustomer(int customerID) throws CouponSystemException {
	Customer currCustomer = customerRepository.getById(customerID);
	if (currCustomer != null) {
		System.out.println("*** Company by ID: " + currCustomer.getId());
		return currCustomer;
	} else {
		throw new CouponSystemException("*** Customer with id " + customerID + " dosen't exists.");
	}
	}

}