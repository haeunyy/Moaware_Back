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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.dto.WorkPkDto;
import com.greedy.moaware.work.dto.WorkTimeDto;
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
		
		log.info("[WorkController] : @AuthenticationPrincipal emp : {} =========================================================",emp);
		
		log.info("[WorkController] : page : {}", page);
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
	
	
	/* 사원 번호 + 월별 근태 현황 조회 나중위 위와 같이 수정 필요 */
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
	
	/* 출근 버튼 클릭시 인서트 */
	@PostMapping("start")
	public ResponseEntity<ResponseDto> insertStart(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestBody String workDate) {
		
		
	    Date parsedDate = null;
	    try {
	        parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(workDate);
	    } catch (ParseException e) {
	        // 파싱 실패 시 예외 처리
	        e.printStackTrace();
	        // 적절한 예외 처리 로직 작성
	    }
		
		WorkTimeDto workTimeDto = new WorkTimeDto();
				
		WorkPkDto workPkDto = new WorkPkDto();
		workPkDto.setEmpCode(emp.getEmpCode());
		workPkDto.setWorkDate(parsedDate);
		
		workTimeDto.setWorkPk(workPkDto);
		
		log.info("[WorkController] workTimeDto.workpK.workDate : {}", workTimeDto.getWorkPk().getWorkDate());
		
		workService.insertStart(workTimeDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "출근등록 완료"));
	}
	
	@PutMapping("quit")
	public ResponseEntity<ResponseDto> timeQuit(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestBody String quitTime) {
		
		
	    Date parsedDate = null;
	    try {
	        parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(quitTime);
	    } catch (ParseException e) {
	        
	        e.printStackTrace();
	        
	    }
		
		WorkTimeDto workTimeDto = new WorkTimeDto();
				
		WorkPkDto workPkDto = new WorkPkDto();
		workPkDto.setEmpCode(emp.getEmpCode());
		workTimeDto.setQuitTime(parsedDate);
		workTimeDto.setWorkPk(workPkDto);
		
		log.info("[WorkController] workTimeDto.workpK.workDate : {}", workTimeDto.getWorkPk().getEmpCode());

		
		log.info("[WorkController] parsedDate : {}", parsedDate);
		
		workService.quitTime(workTimeDto);
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "퇴근등록 완료"));
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
