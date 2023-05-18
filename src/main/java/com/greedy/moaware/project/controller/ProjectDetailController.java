package com.greedy.moaware.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.project.service.ProjDetailService;

@RestController
@RequestMapping("/proj")
public class ProjectDetailController {

	private final ProjDetailService projService;
	
	public ProjectDetailController(ProjDetailService projService) {
		this.projService = projService;
	}
	
	
	/* 프로젝트 상세 조회 */
	@GetMapping("/detail/{projCode}")
	public ResponseEntity<ResponseDto> selectProjDetail(@PathVariable int projCode){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "프로젝트 상세 조회 성공", projService.selectProjDetail(projCode)));
	}
	
	/* 프로젝트의 업무 리스트 조회 */
	@GetMapping("/task/{projCode}")
	public ResponseEntity<ResponseDto> selectTaskList(@PathVariable int projCode){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 리스트 조회 성공", projService.selectTaskList(projCode)));
	}
	
	
	
	
}
 