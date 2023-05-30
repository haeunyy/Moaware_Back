package com.greedy.moaware.review.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.employee.dto.FileCategoryDto;
import com.greedy.moaware.exception.NotLogin;
import com.greedy.moaware.project.dto.TaskDto;
import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.project.repository.TaskRepository;
import com.greedy.moaware.review.dto.ReviewEmpDto;
import com.greedy.moaware.review.dto.TaskReviewDto;
import com.greedy.moaware.review.entity.TaskReview;
import com.greedy.moaware.review.repository.ReviewEmpRepository;
import com.greedy.moaware.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService { 
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	
	@Value("${image.image-dir}")
	private String IMAGE_DIR;
	
	private ReviewRepository reviewRepository;
	private TaskRepository taskRepository;
	private ReviewEmpRepository empRepository;
	private ModelMapper modelMapper;
	
	public ReviewService(ReviewRepository reviewRepository
			, ModelMapper modelMapper
			, TaskRepository taskRepository
			, ReviewEmpRepository empRepository) {
		this.reviewRepository = reviewRepository;
		this.modelMapper = modelMapper;
		this.taskRepository = taskRepository;
		this.empRepository = empRepository;
	}
	
	
	/* 업무 댓글 전체 조회 */
	public List<TaskReviewDto> selectTaskReviews(int taskCode) {
		
		log.info("[ReviewService] selectTaskReviews start =============================================");
		log.info("[ReviewService] taskCode : {}", taskCode);
		
//		Pageable pageable = PageRequest.of(page ,6, Sort.by("type").descending());

		Task task = taskRepository.findById(taskCode)
				.orElseThrow(()-> new IllegalArgumentException(taskCode + "번 업무가 존재하지 않습니다."));

		String status = "Y";
		List<TaskReview> taskList = reviewRepository.findByTask(task);
//		Slice<TaskReview> taskList = reviewRepository.findByTaskAndStatus(pageable, task, status);
		
		List<TaskReviewDto> dto = taskList.stream()
				.map(review -> modelMapper.map(review, TaskReviewDto.class)).collect(Collectors.toList());
		
		for (TaskReviewDto review : dto ) {
			
			for(FileCategoryDto file : review.getEmp().getFileCategory() ) {
				
				String type = "emp";
				
				if (file.getFCategoryType().equals(type)) {
					file.getFile().setFilePath(
							IMAGE_URL + file.getFile().getFilePath());
				}
		    }
		}
		
		log.info("[ReviewService] taskList : {}", taskList);
		
		log.info("[ReviewService] selectTaskReviews end =============================================");

		return dto;
	}


	/* 댓글 등록 */
	@Transactional
	public void taskReviewRegist(int taskCode, TaskReviewDto review, AuthEmpDto emp) {
		
		log.info("[ReviewService] taskReviewRegist start =============================================");
		log.info("[ReviewService] emp : {}", emp);

		Task task = taskRepository.findById(taskCode)
				.orElseThrow(()-> new IllegalArgumentException(taskCode + "번 업무가 존재하지 않습니다."));
		
		ReviewEmpDto reviewer = modelMapper.map(empRepository.findById(emp.getEmpCode())
				.orElseThrow(()-> new NotLogin("로그인 후 이용 가능합니다.")), ReviewEmpDto.class);
		
		review.setTask(modelMapper.map(task, TaskDto.class));
		review.setEmp(reviewer);
		review.setDate(new Date());
		review.setType("프로젝트");
		review.setStatus("Y");

		reviewRepository.save(modelMapper.map(review, TaskReview.class));
		
		log.info("[ReviewService] review : {}", review);
		
//		reviewRepository.save(modelMapper.map(review, TaskReviewDto));
		log.info("[ReviewService] taskReviewRegist end =============================================");
	}

}
