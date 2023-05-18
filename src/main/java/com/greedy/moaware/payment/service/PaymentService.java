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
	
	
	/* 결재 대기 조회 */
	
	/* 결재 진행 조회 */
	
	/* 결재 완료 조회*/

	/* 반려 조회 */
	
	/* 임시 저장 조회 */
	
	/* 서명 조회 */

	


}
