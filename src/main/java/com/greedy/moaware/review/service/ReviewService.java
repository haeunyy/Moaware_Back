package com.greedy.moaware.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.project.repository.TaskRepository;
import com.greedy.moaware.review.dto.TaskReviewDto;
import com.greedy.moaware.review.entity.TaskReview;
import com.greedy.moaware.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService { 
	
	private ReviewRepository reviewRepository;
	private TaskRepository taskRepository;
	private ModelMapper modelMapper;
	
	public ReviewService(ReviewRepository reviewRepository
			, ModelMapper modelMapper
			, TaskRepository taskRepository) {
		this.reviewRepository = reviewRepository;
		this.modelMapper = modelMapper;
		this.taskRepository = taskRepository;
	}
	
	
	/* 업무 댓글 전체 조회 */
	public List<TaskReviewDto> selectTaskReviews(int taskCode) {
		
		log.info("[ReviewService] selectTaskReviews start =============================================");
		log.info("[ReviewService] taskCode : {}", taskCode);
		
		Pageable page = PageRequest.of(0 ,6, Sort.by("type").descending());

		Task task = taskRepository.findById(taskCode)
				.orElseThrow(()-> new IllegalArgumentException(taskCode + "번 업무가 존재하지 않습니다."));

		String status = "Y";
		Slice<TaskReview> taskList = reviewRepository.findByTaskAndStatus(page, task, status);
		
		log.info("[ReviewService] taskList : {}", taskList);
		
		log.info("[ReviewService] selectTaskReviews end =============================================");

		return taskList.stream()
				.map(review -> modelMapper.map(review, TaskReviewDto.class))
				.collect(Collectors.toList());
	}

}
