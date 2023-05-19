package com.greedy.moaware.project.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.project.dto.CreateProjectEmpDto;
import com.greedy.moaware.project.dto.ProjectDto;
import com.greedy.moaware.project.entity.CreateProjectEmp;
import com.greedy.moaware.project.entity.ProjEmp;
import com.greedy.moaware.project.entity.Project;
import com.greedy.moaware.project.repository.CreateProjectRepository;
import com.greedy.moaware.project.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectService {
	
	private final ModelMapper modelMapper;
	private final ProjectRepository projResitory;
	private final AuthEmpRepository authEmpRepository;
	private final CreateProjectRepository createProjectRepository;
	
	public ProjectService(ProjectRepository projResitory, ModelMapper modelMapper, AuthEmpRepository authEmpRepository, CreateProjectRepository createProjectRepository) {
		this.modelMapper = modelMapper;
		this.projResitory = projResitory;
		this.authEmpRepository = authEmpRepository;
		this.createProjectRepository = createProjectRepository;
	}
	
	public Page<ProjectDto> selectMyProgressProj(Integer empCode, int page) {
		
		log.info("[ProjectService] selectMyproject start ===========================");
		log.info("[ProjectService] empCode : {}", empCode);
		
		
		AuthEmp emp = authEmpRepository.findById(empCode)
				.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
		ProjEmp projEmp = new ProjEmp();
		projEmp.setEmpCode(empCode);

		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("projCode").descending());
		Page<Project> projList = projResitory.findByEmployeeEmpCodeAndProjStatus(pageable, projEmp.getEmpCode(), "진행중");
		
		Page<ProjectDto> projDtoList = projList.map(proj -> modelMapper.map(proj, ProjectDto.class));
		
		
		return projDtoList;
	}

	public Page<ProjectDto> selectMyDoneProj(Integer empCode, int page) {
		
		log.info("[ProjectService] selectMyDoneProj start ===========================");
		log.info("[ProjectService] empCode : {}", empCode);
		
		AuthEmp emp = authEmpRepository.findById(empCode)
				.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
		ProjEmp projEmp = new ProjEmp();
		projEmp.setEmpCode(empCode);
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("projCode").descending());
		Page<Project> projList = projResitory.findByEmployeeEmpCodeAndProjStatus(pageable, projEmp.getEmpCode(), "완료");
		
		Page<ProjectDto> projDtoList = projList.map(proj -> modelMapper.map(proj, ProjectDto.class));
		
		
		return projDtoList;
	}
	
	@Transactional
	public void createPorj(CreateProjectEmpDto projectDto) {
		
	/*프로젝트 테이블이 정보 입력*/
		
	log.info("[ProjectService] createPorj start ===========================");
	
	createProjectRepository.save(modelMapper.map(projectDto, CreateProjectEmp.class));
		
	log.info("[ProjectService] createPorj end ===========================");	
		
		
		
	}

}
