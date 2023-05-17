package com.greedy.moaware.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
