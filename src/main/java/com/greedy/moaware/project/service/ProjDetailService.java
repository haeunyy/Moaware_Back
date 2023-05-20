package com.greedy.moaware.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.project.dto.ProjectDto;
import com.greedy.moaware.project.dto.TaskDto;
import com.greedy.moaware.project.entity.Project;
import com.greedy.moaware.project.repository.ProjectRepository;
import com.greedy.moaware.project.repository.TaskRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjDetailService {
	
	private ModelMapper modelMapper;
	private TaskRepository taskRepository;
	private ProjectRepository projResitory;
	
	public ProjDetailService(ModelMapper modelMapper
			, TaskRepository taskRepository
			, ProjectRepository projResitory) {
		this.modelMapper = modelMapper;
		this.taskRepository = taskRepository;
		this.projResitory = projResitory;
	}

	
	/* 프로젝트 상세 조회 */
	public ProjectDto selectProjDetail(int projCode) {
		
		log.info("[ProjDetailService] selectProjDetail start =============================================");
		
		Project proj = projResitory.findById(projCode)
				.orElseThrow(()-> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
		
		log.info("[ProjDetailService] selectProjDetail end =============================================");
		
		return modelMapper.map(proj, ProjectDto.class);
	}

	
	/* 프로젝트의 업무 리스트 조회 */
	public List<TaskDto> selectTaskList(int projCode) {

		log.info("[ProjDetailService] selectProjDetail start =============================================");

		Project proj = projResitory.findById(projCode)
				.orElseThrow(()-> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));		
		
		List<TaskDto> taskList = taskRepository.findByAll(proj.getProjCode()) 
				.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
		
		log.info("[ProjDetailService] taskList : {}",taskList);
		
		log.info("[ProjDetailService] selectProjDetail end =============================================");
		
		return taskList;
	}
	
	


}
