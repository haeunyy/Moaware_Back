package com.greedy.moaware.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.project.entity.CreateProject;
import com.greedy.moaware.project.entity.Project;

public interface CreateProjectRepository extends JpaRepository<CreateProject, Integer>{

	Page<CreateProject> findByEmployeeEmpCodeAndProjStatus(Pageable pageable, Integer emp, String projStatus);

}
