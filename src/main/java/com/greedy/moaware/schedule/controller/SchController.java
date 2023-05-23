package com.greedy.moaware.schedule.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.schedule.entity.Schedule;
import com.greedy.moaware.schedule.service.SchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/schedule")
public class SchController {

	private final SchService schService;

	public SchController(SchService schService) {
		this.schService = schService;
	}
	
	/* 전체 캘린더 조회 - 생성자, 참여자 일정 모두 포함 */
	@GetMapping("/calendar")
	public ResponseEntity<ResponseDto> mySchList(@AuthenticationPrincipal AuthEmpDto emp) {
	
		log.info("[SchController] : mySchList start ===================================== ");
		
//		List<Schedule> schedules = schService.getScheduleListByUser(emp.getEmpCode());
		
		List<Schedule> schedules = schService.getScheduleListByUser(emp.getEmpCode());
		
		log.info("[SchController] : mySchList  end  ===================================== ");

		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "전체 캘린더 조회 완료"));
	
	}
	
	@GetMapping("/hello")
	public String hellowolrd() {
		return "안냥 이거는 한번해보는 실험이야";
	}
	
}
