package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exception.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;

@Service
@Transactional
@Scope("prototype")
public class CompanyService extends ClientService {

	private Integer companyId;
	private CompanyRepository companyRepository;
	private CouponRepository couponRepository;
	
	public CompanyService(CompanyRepository companyRepository, CouponRepository couponRepository) {
		super();
		this.companyRepository = companyRepository;
		this.couponRepository = couponRepository;
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Company currcompany = companyRepository.getCompany(email, password);
		if (currcompany != null) {
			this.companyId = currcompany.getId();
			System.out.println("*** Welcome Company " + companyId + " ! ***");
			return true;
		} else {
			throw new CouponSystemException("*** Company login failed.");
		}
	}

	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (couponRepository.existsByCompanyIdAndTitle(companyId, coupon.getTitle())) {
			throw new CouponSystemException(
					"*** You can't add a Coupon, there is already a coupon with the same title.");
		}
		coupon.setCompany(companyRepository.getById(companyId));
		couponRepository.save(coupon);
		System.out.println("*** New coupon was added successfully.");
	}

	public void updateCoupon(Coupon coupon, Integer couponId) throws CouponSystemException {
		Coupon currCoupon = couponRepository.getById(couponId);
		if (currCoupon != null && currCoupon.getCompany().getId() == this.companyId) {
			currCoupon.setCategory(coupon.getCategory());
			currCoupon.setTitle(coupon.getTitle());
			currCoupon.setDescription(coupon.getDescription());
			currCoupon.setStartDate(coupon.getStartDate());
			currCoupon.setEndDate(coupon.getEndDate());
			currCoupon.setAmount(coupon.getAmount());
			currCoupon.setPrice(coupon.getPrice());
			currCoupon.setImage(coupon.getImage());
			System.out.println("*** Coupon with id " + couponId + " was updated successfully.");
		} else {
			throw new CouponSystemException("*** Coupon with id " + couponId + " does not exist.");
		}
	}

	public void deleteCoupon(int couponId) throws CouponSystemException {
		Coupon currCoupon = couponRepository.getById(couponId);
		if (currCoupon != null) {
			couponRepository.deleteById(couponId);
			System.out.println("*** Coupon " + couponId + " was deleted.");
		}else {
			throw new CouponSystemException("*** Coupon with id " + couponId + " does not exist.");
		}
	}

	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		List<Coupon> currCoupons = couponRepository.getCompanyCoupons(this.companyId);
		if(currCoupons != null) {
			System.out.println("*** All company's coupons: ");
			return currCoupons;
		}else {
			throw new CouponSystemException("*** The company does not have coupons.");
		}
	}

	public List<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		List<Coupon> currCoupons = couponRepository.getCompanyCoupons(this.companyId, category);
		if (currCoupons != null) {
			System.out.println("*** All company's coupons by category: ");
			return currCoupons;
		}else {
			throw new CouponSystemException("*** There are no coupons in this category.");
		}
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		List<Coupon> currCoupons = couponRepository.getCompanyCoupons(this.companyId, maxPrice);
		if (currCoupons != null) {
		System.out.println("*** All company's coupons by maximum price: ");
		return currCoupons;
		}else {
			throw new CouponSystemException("*** There are no coupons up to such a price.");
		}
	}

	public Company getCompanyDetails() throws CouponSystemException {
		try {
			Company currCompany = companyRepository.getById(this.companyId);
			System.out.println("*** Company details: ");
			return currCompany;
		} catch (Exception e) {
			throw new CouponSystemException("*** Unable to import data.");
		}
	}

	public Coupon getCouponById(int couponId) throws CouponSystemException {
		Coupon currCoupon = couponRepository.getById(couponId);
		if (currCoupon != null) {
		System.out.println("*** Coupon by id: " + couponId);
		return currCoupon;
		}else {
			throw new CouponSystemException("*** Coupon with id " + couponId + " does not exist.");
		}
	}

}