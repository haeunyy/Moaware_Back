package com.greedy.moaware.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

	Project findByProjCode(int projCode);


	
	
	
	
	
	
	
	
	
	











	Page<Project> findByEmployeeEmpCodeAndProjStatus(Pageable pageable, Integer emp, String projStatus);










}
