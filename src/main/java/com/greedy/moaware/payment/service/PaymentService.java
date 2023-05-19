package com.greedy.moaware.payment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.payment.dto.PaymentDto;
import com.greedy.moaware.payment.entity.Payment;
import com.greedy.moaware.payment.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	private final ModelMapper modelMapper;
	
	public PaymentService(ModelMapper modelMapper, PaymentRepository paymentRepository) {
		this.modelMapper = modelMapper;
		this.paymentRepository = paymentRepository;
		
	}
	
	/* 테스트용 */
	public List<PaymentDto> test() {
		
		log.info("[PaymentService] test start ============================== ");
		
		List<Payment> payList = paymentRepository.findAll();
		
		log.info("[PaymentService] payList : {}" , payList);
		
		List<PaymentDto> paysDto = payList.stream().map( pay -> modelMapper.map(pay, PaymentDto.class)).collect(Collectors.toList());
		
		log.info("[PaymentService] paysDto : {}" , paysDto);
		
		log.info("[PaymentService] test end ============================== ");
		
		return paysDto;
	}
	
	/* 결재 대기 조회 */
	
	/* 결재 진행 조회 */
	
	/* 결재 완료 조회*/

	/* 반려 조회 */
	
	/* 임시 저장 조회 */
	
	/* 서명 조회 */

	


}
