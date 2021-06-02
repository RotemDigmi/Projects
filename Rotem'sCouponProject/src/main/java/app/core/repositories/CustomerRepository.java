package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.core.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

	@Query("from Customer c where c.email = :email and c.password = :password")
	Customer getCustomer(String email, String password);

	Customer getById(Integer id);
	
}