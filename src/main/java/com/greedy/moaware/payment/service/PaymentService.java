package com.greedy.moaware.payment.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final ModelMapper modelMapper;
	
	public PaymentService(ModelMapper modelMapper, PaymentRepository paymentRepository) {
		this.modelMapper = modelMapper;
		this.paymentRepository = paymentRepository;
		
		
	}
	


}
