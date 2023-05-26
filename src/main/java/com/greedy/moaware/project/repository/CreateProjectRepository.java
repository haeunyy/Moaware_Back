package com.greedy.moaware.project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.project.entity.CreateProject;

public interface CreateProjectRepository extends JpaRepository<CreateProject, Integer>{

	Page<CreateProject> findByEmployeeEmpCodeAndProjStatus(Pageable pageable, Integer emp, String projStatus);
	
	@Query("SELECT p.projCode FROM CreateProject p ORDER BY p.projCode DESC")
	CreateProject findByProjCode(Integer projCode);

}
