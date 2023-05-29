package com.greedy.moaware.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.common.ResponseDto;
import com.greedy.moaware.review.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {
	
	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	
	@GetMapping("/task/{taskCode}")
	public ResponseEntity<ResponseDto> selectTaskReviews(@PathVariable int taskCode){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "업무 리뷰 조회 완료", reviewService.selectTaskReviews(taskCode)));
	}

}
