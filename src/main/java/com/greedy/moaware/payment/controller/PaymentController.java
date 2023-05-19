package com.greedy.moaware.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.payment.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;	
	}	
	
	
	@GetMapping("/draft")
	public ResponseEntity<ResponseDto> test(){
		
		log.info("[PaymentController] test start ============================== ");
		
		
		
		log.info("[PaymentController] test end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 전자결재 조회.", paymentService.test() ));
	}
}
