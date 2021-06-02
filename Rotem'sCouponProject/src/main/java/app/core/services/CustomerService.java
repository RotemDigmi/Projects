package app.core.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
@Scope("prototype")
public class CustomerService extends ClientService {

	private Integer customerId;
	@Autowired
	private EntityManager em;
	private CustomerRepository customerRepository;
	private CouponRepository couponRepository;

	public CustomerService(CustomerRepository customerRepository, CouponRepository couponRepository) {
		super();
		this.customerRepository = customerRepository;
		this.couponRepository = couponRepository;
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Customer currCustomer = customerRepository.getCustomer(email, password);
		if (currCustomer != null) {
			this.customerId = currCustomer.getId();
			System.out.println("*** Customer " + currCustomer.getId() + " sign in:" + " Welcome "
					+ currCustomer.getFirstName() + " " + currCustomer.getLastName() + "!");
			return true;
		} else {
			throw new CouponSystemException("*** Customer login failed.");
		}
	}

	public void purchaseCoupon(Integer couponId) throws CouponSystemException {
		Customer currCustomer = customerRepository.getById(this.customerId);
		Coupon currCoupon = couponRepository.getById(couponId);
		Long currTime = System.currentTimeMillis();
		if (currCoupon != null) {
		if (currCoupon.getAmount() == 0 || currCoupon.getEndDate().before(new Date(currTime))) {
			throw new CouponSystemException("*** Coupon purchase is not available, amount or end date are wrong.");
		}
		List<Coupon> currList = currCustomer.getCoupons();
		for (Coupon oneCoupon : currList) {
			if (currCoupon.getId() == oneCoupon.getId()) {
				throw new CouponSystemException(
						"*** Coupon purchase is not available, This coupon was previously purchased.");
			}
		}
		currCoupon.setAmount(currCoupon.getAmount() - 1);
		currList.add(currCoupon);
		System.out.println("*** Coupon with id " + currCoupon.getId() + ", was purchased successfully.");
		customerRepository.save(currCustomer);
		}else {
			throw new CouponSystemException("Coupon with id " + couponId + " does not exist.");
		}
	}

	public List<Coupon> getCustomerCoupons() throws CouponSystemException {
		List<Coupon> customerCoupons = customerRepository.getById(customerId).getCoupons();
		if (customerCoupons != null) {
		System.out.println("*** All customer's coupons: ");
		return customerCoupons;
		}else {
			throw new CouponSystemException("*** You have no coupons.");
		}

	}

	public List<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {
		Customer currCustomer = customerRepository.getById(customerId);
		if (currCustomer != null) {
			em.refresh(currCustomer);
			List<Coupon> customerCoupons = currCustomer.getCoupons();
			List<Coupon> customerCouponsByCategory = new ArrayList<Coupon>();
			for (Coupon coupon : customerCoupons) {
				if (coupon.getCategory().equals(category)) {
					customerCouponsByCategory.add(coupon);
				}
			}
			System.out.println("*** All customer's coupons by category: ");
			return customerCouponsByCategory;
		} else {
			throw new CouponSystemException("*** There are no coupons in this category.");
		}
	}

	public List<Coupon> getCustomerCoupons(double maxPrice) throws CouponSystemException {
		Customer currCustomer = customerRepository.getById(customerId);
		if (currCustomer != null) {
			em.refresh(currCustomer);
			List<Coupon> customerCoupons = currCustomer.getCoupons();
			List<Coupon> customerCouponsBymaxPrice = new ArrayList<Coupon>();
			for (Coupon coupon : customerCoupons) {
				if (coupon.getPrice() < maxPrice) {
					customerCouponsBymaxPrice.add(coupon);
				}
			}
			System.out.println("*** All customer's coupons by maximum price: ");
			return customerCouponsBymaxPrice;
		} else {
			throw new CouponSystemException("*** There are no coupons at such a price.");
		}
	}

	public Customer getCustomerDetails() throws CouponSystemException {
		try {
			Customer currCustomer = customerRepository.getById(this.customerId);
			System.out.println("*** Customer deatils: ");
			return currCustomer;
		} catch (Exception e) {
			throw new CouponSystemException("*** Unable to import data.");
		}
	}

	public Coupon getCouponById(int couponId) throws CouponSystemException {
		Coupon currCoupon = couponRepository.getById(couponId);
		if (currCoupon != null) {
			return currCoupon;
		}else {
			throw new CouponSystemException("*** Coupon does not exist.");
		}
	}

}