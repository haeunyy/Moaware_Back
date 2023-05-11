package com.greedy.moaware.work.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.common.paging.Pagenation;
import com.greedy.moaware.common.paging.PagingButtonInfo;
import com.greedy.moaware.common.paging.ResponseDtoWithPaging;
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.service.WorkService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/work/my")
public class WorkController {
	
	private final WorkService workService;
	
	public WorkController(WorkService workService) {
		this.workService = workService;
	}
	
	@GetMapping("/works")
	public ResponseEntity<ResponseDto> selectWorkList(@RequestParam Integer empCode, @RequestParam(name = "page", defaultValue = "1") int page) {
		
		log.info("[WorkController] : selectWorkList Start =========================================================");
		log.info("[WorkController] : page : {}", page);
		
		Page<WorkDto> workDtoList = workService.selectWorkList(empCode, page);
		
		PagingButtonInfo pageInfo = Pagenation.getPagingButtonInfo(workDtoList);
		ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
		responseDtoWithPaging.setPageInfo(pageInfo);
		responseDtoWithPaging.setData(workDtoList.getContent());
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
	}
}
