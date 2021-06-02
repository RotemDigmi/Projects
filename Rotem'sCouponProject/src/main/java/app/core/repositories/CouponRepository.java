package app.core.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	
	Coupon getById(Integer id);
	
	boolean existsByCompanyIdAndTitle(Integer company_id, String title);
	
	@Query("from Coupon c where c.company.id = :company_id")
	List<Coupon> getCompanyCoupons(Integer company_id);
	
	@Query("from Coupon c where c.company.id = :company_id and c.category = :category")
	List<Coupon> getCompanyCoupons(Integer company_id , Category category);
	
	@Query("from Coupon c where c.company.id = :company_id and c.price <= :maxPrice")
	List<Coupon> getCompanyCoupons(Integer company_id , Double maxPrice);
	
	@Query("from Coupon c where c.endDate < :currDate")
	List<Coupon> getEndDateCoupons(Date currDate);
	
}