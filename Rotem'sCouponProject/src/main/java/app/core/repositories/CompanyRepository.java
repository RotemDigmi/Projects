package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.core.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	boolean existsByEmailAndPassword(String email, String password);
	
	boolean existsByNameOrEmail(String name, String email);
	
	@Query("from Company c where c.email = :email and c.password = :password")
	Company getCompany(String email, String password);
	
	Company getById(Integer id);
	
}