package com.greedy.moaware.payment.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.payment.dto.PayAttachedFileDto;
import com.greedy.moaware.payment.dto.PaymentDto;
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
	
	
	/* 기안자 기안문 전체 조회 */
	@GetMapping("/main")
	public ResponseEntity<ResponseDto> PaymentAllList(@AuthenticationPrincipal AuthEmpDto payEmp){
		
		log.info("[PaymentController] PaymentList start ============================== ");

		
		log.info("[PaymentController] PaymentList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 전자결재 조회.", paymentService.paymentAllList(payEmp.getEmpCode()) ));
	}
	
	
	/* 기안자로 기안문 진행중 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> PaymentList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.paymentList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList.getContent());
		
		log.info("[PaymentController] PaymentList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "진행중 전자결재 조회.", responseDtoPage ));
	}
	
	
	
	/* 기안서 양식 조회*/
	@GetMapping("/draft")
	public ResponseEntity<ResponseDto> FormSelect(@AuthenticationPrincipal AuthEmpDto payEmp){
		
		log.info("[PaymentController] FormSelect start ============================== ");
		
		log.info("[PaymentController] FormSelect payEmp :  {}", payEmp.getEmpCode());
				
		Map<String, Object> resultMap = paymentService.formSelect(payEmp.getEmpCode());
		
		log.info("[PaymentController] FormSelect pay :  {}", resultMap);

		
		log.info("[PaymentController] FormSelect end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 전자결재 양식 조회.", resultMap));
	}
	
	
	/* 기안서 저장*/
	@PostMapping("/draft")
	public ResponseEntity<ResponseDto> insertPayment(@AuthenticationPrincipal AuthEmpDto payEmp, @ModelAttribute PayAttachedFileDto payAttachedFile){
		
		log.info("[PaymentController] insertPayment start ============================== ");
		
		log.info("[PaymentController] insertPayment payEmp :  {}", payEmp);
		log.info("[PaymentController] insertPayment payAttachedFile :  {}", payAttachedFile);
				
		paymentService.insertPayment(payEmp.getEmpCode(), payAttachedFile);
		
		log.info("[PaymentController] insertPayment end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, " 기안문 저장 완료"));
	}
	
	/* 결재 대기 문서 전체 조회 */
	@GetMapping("/memberList")
	public ResponseEntity<ResponseDto> PaymentMemberList(@AuthenticationPrincipal AuthEmpDto payEmp, @RequestParam(name="page", defaultValue="1") int page){
		
		log.info("[PaymentController] PaymentList start ============================== ");
		
		Page<PaymentDto> paymentDtoList = paymentService.paymentMemberList(payEmp.getEmpCode(), page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(paymentDtoList);
		
		ResponseDtoWithPaging responseDtoPage = new ResponseDtoWithPaging();
		responseDtoPage.setPageInfo(pageInfo);
		responseDtoPage.setData(paymentDtoList);
		
		long totalElements = paymentDtoList.getTotalElements();
		log.info("[PaymentController] PaymentList 총 요소 수 : {} ", totalElements);
	
		
		log.info("[PaymentController] PaymentList end ============================== ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 전자결재 조회.", responseDtoPage ));
	}
	
	
	
}
