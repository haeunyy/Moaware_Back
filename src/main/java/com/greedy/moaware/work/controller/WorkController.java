package com.greedy.moaware.work.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.service.WorkService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/work")
public class WorkController {
	
	private final WorkService workService;
	
	public WorkController(WorkService workService) {
		this.workService = workService;
	}
	
	/* 자기 근태 현항 조회 */
	@GetMapping("/works/{workDate}")
	public ResponseEntity<ResponseDto> myWorkList(@AuthenticationPrincipal AuthEmpDto emp,
			@PathVariable String workDate,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		
		log.info("[WorkController] : myWorkList Start =========================================================");
		
		log.info("[WorkController] : page : {}", page);
		
		   // workDate를 Date 타입으로 파싱
	    Date parsedDate = null;
	    try {
	        parsedDate = new SimpleDateFormat("yyyy-MM").parse(workDate);
	    } catch (ParseException e) {
	        // 파싱 실패 시 예외 처리
	        e.printStackTrace();
	        // 적절한 예외 처리 로직 작성
	    }
		
		
		
		Page<WorkDto> workDtoList = workService.selectMyWorkList(emp.getEmpCode(), parsedDate, page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(workDtoList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(workDtoList.getContent());
		
		log.info("[WorkController] : myWorkList end =========================================================");
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "내 근태 현황 조회 완료", responseDtoWithPaging));
	}
	
	
	/* 사원 번호 + 월별 근태 현황 조회 */
	@GetMapping("/works/{empCode}/{workDate}")
	public ResponseEntity<ResponseDto> selectWorkList(
	        @PathVariable Integer empCode, 
	        @PathVariable @DateTimeFormat(pattern = "yyyy-MM") Date workDate, 
	        @RequestParam(name = "page", defaultValue = "1") int page) {
		
		log.info("[WorkController] : selectWorkList Start =========================================================");
		log.info("[WorkController] : page : {}", page);
		
		Page<WorkDto> workDtoList = workService.selectWorkList(empCode, workDate, page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(workDtoList);
		
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(workDtoList.getContent());
		
		log.info("[WorkController] : selectWorkList end =========================================================");
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));	
		}
	
	/* 이름 + 날짜로 근무 조회 */
//	@GetMapping("name/{empName}/{workDate}")
//	public ResponseEntity<ResponseDto> selectWorkList(
//	        @PathVariable String empName, 
//	        @PathVariable @DateTimeFormat(pattern = "yyyy-MM") LocalDate workDate, 
//	        @RequestParam(name = "page", defaultValue = "1") int page) {
//	    
//	    log.info("[WorkController] : selectWorkList Start =========================================================");
//	    log.info("[WorkController] : page : {}", page);
//	    
//	    Page<Work> workDtoList = workService.findWorksByNameAndDate(empName, workDate, page);
//	    
//	    PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(workDtoList);
//	    
//	    ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//	    responseDtoWithPaging.setPageInfo(pageInfo);
//	    responseDtoWithPaging.setData(workDtoList.getContent());
//	    
//	    log.info("[WorkController] : selectWorkList end =========================================================");
//	    
//	    return ResponseEntity
//	            .ok()
//	            .body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));    
//	}
}
