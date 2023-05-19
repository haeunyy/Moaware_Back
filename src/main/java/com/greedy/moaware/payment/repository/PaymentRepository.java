package com.greedy.moaware.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	
	
	@EntityGraph(attributePaths= {"form"})
	List<Payment> findAll();

}
