package com.greedy.moaware.project.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.project.dto.CreateProjectDto;
import com.greedy.moaware.project.service.ProjectService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/proj")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	//진행중 프로젝트 조회 
	@GetMapping("progressProj")
	public ResponseEntity<ResponseDto> selectMyProgressProj(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		log.info("[ProjectController] : selectMyProgressProj start =========================================================");

		Page<CreateProjectDto> projDtoList = projectService.selectMyProgressProj(emp.getEmpCode(), page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(projDtoList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(projDtoList.getContent());

		log.info("[ProjectController] : myWorkList end =========================================================");

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "진행 중 프로젝트 조회 완료", responseDtoWithPaging));
	}
	//완료한 프로젝트 조회
	@GetMapping("doneProj")
	public ResponseEntity<ResponseDto> selectMyDoneProj(@AuthenticationPrincipal AuthEmpDto emp,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		log.info("[ProjectController] : selectMyDoneProj start =========================================================");

		Page<CreateProjectDto> projDtoList = projectService.selectMyDoneProj(emp.getEmpCode(), page);
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(projDtoList);

		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(projDtoList.getContent());

		log.info("[ProjectController] : selectMyDoneProj end =========================================================");

		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "완료 프로젝트 조회 완료", responseDtoWithPaging));
	}
	//프로젝트 생성
	@PostMapping("CreateProj")
	public ResponseEntity<ResponseDto> createProj(@RequestBody CreateProjectDto projectDto,
			@AuthenticationPrincipal AuthEmpDto emp) {
		
		log.info("[projectDto.getStartDate()] projectDto.getStartDate(){}", projectDto.getStartDate());
		log.info("[projectDto.getStartDate()] projectDto.getStartDate().toString() {}", projectDto.getStartDate().toString());
		
		projectDto.setEmployee(emp);
		// 객체에 담아서 전송하는 것은 바로 파싱이 된다.
	    Date parseStartDate = projectDto.getStartDate();
	    Date parseEndDate = projectDto.getEndDate();
	    
		
	    projectDto.setStartDate(parseStartDate);
	    projectDto.setEndDate(parseEndDate);
	    projectDto.setEmployee(emp);
		projectService.createPorj(projectDto);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "프로젝트 생성 성공"));
	}
}
