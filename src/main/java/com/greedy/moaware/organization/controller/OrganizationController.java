package com.greedy.moaware.organization.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	/* 조직도 전체 검색 */
	@GetMapping("/list")
	public ResponseEntity<ResponseDto> selectOrganizationList(){
		
		log.info("[EmpController] selectOrganizationList start ============================== ");
		
		List<OrganizationDto> orgDtoList = organizationService.selectOranizationList();
		
		
		log.info("[EmpController] selectOrganizationList orgDtoList : {}", orgDtoList );
		log.info("[EmpController] selectOrganizationList end ================================ ");
		
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 사원 조회가 성공하였습니다.", orgDtoList ));
		
	}
	

	
	/* 조직도 이름,직급,직책으로 검색 */
	@GetMapping("/search")
	public ResponseEntity<ResponseDto> selectOrgSearch(@RequestParam(name="search") String search){
		
		log.info("[EmpController] selectOrgSearch start ============================== ");
		log.info("search : {}" , search);
		
		List<OrganizationDto> orgDtoList = organizationService.selectOrgSearch(search);
		
		
		log.info("[EmpController] selectOrgSearch orgDtoList : {}", orgDtoList );
		log.info("[EmpController] selectOrgSearch end ================================ ");
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", orgDtoList ));
	}
	

}
