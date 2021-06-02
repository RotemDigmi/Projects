package app.core.dailyJob;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Coupon;
import app.core.repositories.CouponRepository;

@Component
public class CouponExpirationDailyJob implements Runnable {

	@Autowired
	private CouponRepository couponRepository;
	private boolean quit;
	private Thread jobThread;

	public CouponExpirationDailyJob() {
		super();
		jobThread = new Thread(this, "job");
	}
	
	@PostConstruct
	public void initJob() {
		jobThread.start();
		System.out.println("Daily job start.");
	}

	@PreDestroy
	public void destroyJob() {
		jobThread.interrupt();
		System.out.println("Daily job was done.");
	}
	
	@Override
	@Transactional
	public void run() {
		try {
			while (quit != true) {
				List<Coupon> couponsEndDate = couponRepository.getEndDateCoupons(Date.valueOf(LocalDate.now()));
				synchronized (couponsEndDate) {
					if (couponsEndDate != null) {
						for (Coupon coupon : couponsEndDate) {
							couponRepository.deleteById(coupon.getId());
							System.out.println("An expired coupon has been deleted.");
						}
					}
				}
				System.out.println("Expiration Daily Job: No coupons to deleted.");
				try {
					Thread.sleep(86_400_000);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}