package com.greedy.moaware.organization.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.organization.dto.OrganizationDto;
import com.greedy.moaware.organization.service.OrganizationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/org")
public class OrganizationController {
	
	private OrganizationService organizationService;
	
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	@GetMapping("/List")
	public ResponseEntity<ResponseDto> selectOrganizationList(){
		
		log.info("[EmpController] selectOrganizationList start ============================== ");
		
		List<OrganizationDto> orgDtoList = organizationService.selectOranizationList();
		
		
		log.info("deptDtoList : {}" );
		log.info("[EmpController] selectOrganizationList end ================================ ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", orgDtoList ));
		
	}

}
