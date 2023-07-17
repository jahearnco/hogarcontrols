package com.everphase.hogar.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	boolean existsByUserId(String userId);
	List<Customer> findAll();
	Optional<Customer> findById(Long id);
}