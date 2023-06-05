package com.greedy.moaware.project.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.AuthEmpDto;
import com.greedy.moaware.project.dto.ProjEmpDto;
import com.greedy.moaware.project.dto.ProjectDto;
import com.greedy.moaware.project.dto.TaskDto;
import com.greedy.moaware.project.entity.Project;
import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.project.repository.ProjectRepository;
import com.greedy.moaware.project.repository.TaskRepository;

import lombok.extern.slf4j.Slf4j;

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


	
	/* 업무 리스트 조회 */
	public List<TaskDto> selectTaskList(int projCode) {

		Project proj = projResitory.findById(projCode)
				.orElseThrow(()-> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));		
		
		List<TaskDto> taskList = taskRepository.findByAll(proj.getProjCode()) 
				.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
		
		return taskList;
	}


	/* 업무 조회 */
	public TaskDto selectTask(int taskCode) {

		TaskDto task = modelMapper.map(taskRepository.findById(taskCode)
				.orElseThrow(()-> new IllegalArgumentException("해당 프로젝트 업무가 존재하지 않습니다.")), TaskDto.class);		
		
		return task;
	} 


	/* 업무 등록 */
	@Transactional
	public void taskRegist(AuthEmpDto emp, TaskDto task) {
		
		task.setAuthor(modelMapper.map(emp, ProjEmpDto.class));
		task.setModifyTime(new Date());
		
		taskRepository.save(modelMapper.map(task, Task.class));

	}

	


	/* 프로젝트 상세 조회 */
	public ProjectDto selectProjDetail(int projCode) {
		
		Project proj = projResitory.findById(projCode)
				.orElseThrow(()-> new IllegalArgumentException("해당 프로젝트가 존재하지 않습니다."));
		
		return modelMapper.map(proj, ProjectDto.class);
	}


	/* 업무 수정 */
	@Transactional
	public void taskUpdate(TaskDto taskDto) {
		
		Task task = taskRepository.findById(taskDto.getTaskCode())
				.orElseThrow(()-> new IllegalArgumentException(taskDto.getTaskName()+": 업무가 존재하지 않습니다."));
		
		task.update(
				taskDto.getTaskName(), 
				taskDto.getTaskNotice(),
				taskDto.getType(), 
				taskDto.getStage(), 
				taskDto.getStartDate(), 
				taskDto.getEndDate(), 
				taskDto.getModifyTime()
				);
	}
	
	
	/* 업무 삭제 */
	@Transactional
	public void taskDelete(int taskCode) {

		Task task = taskRepository.findById(taskCode)
				.orElseThrow(()-> new IllegalArgumentException(taskCode +"번 업무가 존재하지 않습니다."));
		
		task.setStatus("N");

	}

}


