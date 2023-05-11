package com.greedy.moaware.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.service.EmpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/emp")
public class EmpController {
	
	private EmpService empService;
	
	public EmpController(EmpService empService) {
		this.empService = empService;
	}
	
	/* 사원 전체 조회 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> selectEmpList(){
		
		log.info("[EmpController] selectEmpList start ============================== ");
		
		List<EmpDto> empDtoList =  empService.selectEmpList();
		
		log.info("empDtoList : {}" , empDtoList);
		log.info("[EmpController] selectEmpList end ================================ ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", empDtoList ));
		
	}
	
	/* 사원 상세 조회*/
	@GetMapping("/detail/{empCode}")
	public ResponseEntity<ResponseDto> selectEmpDetail(@PathVariable Integer empCode){
		
		log.info("[EmpController] selectEmpDetail start ============================== ");
		log.info("empCode : {}" , empCode);
		
		
		log.info("[EmpController] selectEmpDetail end ================================ ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", empService.selectEmpDetail(empCode) ));
	}
	


}
