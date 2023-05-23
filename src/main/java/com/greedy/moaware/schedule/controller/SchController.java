package com.greedy.moaware.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.AuthEmpDto;
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
	
	/* 전체 캘린더 조회 - 생성자 */
	@GetMapping("/all")
	public ResponseEntity<ResponseDto> mySchList(@AuthenticationPrincipal AuthEmpDto emp) {
	
		log.info("[SchController] : mySchList");
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "전체 캘린더 조회 완료"));
		
	}
	
}
