package com.greedy.moaware.leave.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.leave.dto.LeaveDto;
import com.greedy.moaware.leave.dto.LeavePaymentDto;
import com.greedy.moaware.leave.service.LeaveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/leave")
public class LeaveController {
	
	private final LeaveService leaveService;
	
	public LeaveController(LeaveService leaveService) {
		this.leaveService = leaveService;
	}
	
	/*연차 내역 조회*/
	@GetMapping("done")
	public ResponseEntity<ResponseDto> leaveList(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		

		
		Page<LeavePaymentDto> LeavePaymentDtoList = leaveService.selectMyLeaveList(emp.getEmpCode(), page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeavePaymentDtoList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(LeavePaymentDtoList.getContent());

		log.info("[ProjectController] : myWorkList end =========================================================");

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 조회 완료", responseDtoWithPaging));
	}
	/*연차 내역 조회 리브를 주로 해서 가져오기*/
//	@GetMapping("donel/{year}")
//	public ResponseEntity<ResponseDto> leaveList2(@AuthenticationPrincipal AuthEmpDto emp,
//			@PathVariable String year,
//			@RequestParam(name = "page", defaultValue = "1") int page) {
//		
//		Integer leaveYear = Integer.parseInt(year);
//		
////		Page<LeaveDto> LeavePaymentDtoList = leaveService.selectMyLeaveList2(emp.getEmpCode(), leaveYear, page);
//		Page<LeaveDto> LeavePaymentDtoList = leaveService.selectMyLeaveList2(emp.getEmpCode(), leaveYear, page);
//		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(LeavePaymentDtoList);
//		
//		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//		responseDtoWithPaging.setPageInfo(pageInfo);
//		responseDtoWithPaging.setData(LeavePaymentDtoList.getContent());
//
//		log.info("[ProjectController] : myWorkList end =========================================================");
//
//		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "연차 조회 완료", responseDtoWithPaging));
//	}
}
