package com.greedy.moaware.work.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.entity.Work;
import com.greedy.moaware.work.repository.WorkRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkService {
	
	private final WorkRepository workRepository;
	private final EmpRepository empRepository;
	private ModelMapper modelMapper;
	
	public WorkService(WorkRepository workRepository, ModelMapper modelMapper, EmpRepository empRepository) {
		this.modelMapper = modelMapper;
		this.workRepository = workRepository;
		this.empRepository = empRepository;
	}
	
	/* 자기 근태 현황 조회 */
	public Page<WorkDto> selectWorkList(Integer empCode, int page){
		
		log.info("[WorkSerivce] selectWorkList strat ======================================");
		log.info("[WorkSerivce] empCode : {}", empCode);
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("workDate").descending());
		
		Emp emp = empRepository.findById(empCode)
				.orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));
		
		Page<Work> workList = workRepository.findAllByEmployee(emp, pageable);
		
		Page<WorkDto> workDtoList = workList.map(work -> modelMapper.map(work, WorkDto.class));
		
		return workDtoList;
	}

}
