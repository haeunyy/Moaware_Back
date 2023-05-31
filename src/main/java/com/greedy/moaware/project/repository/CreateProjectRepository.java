package com.greedy.moaware.project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.moaware.project.entity.CreateProject;
import com.greedy.moaware.project.entity.ProjParticipantPk;

public interface CreateProjectRepository extends JpaRepository<CreateProject, ProjParticipantPk>{
	
//	@Query(value="SELECT * FROM PROJECT JOIN")
//	Page<CreateProject> findByEmployeeEmpCodeAndProjStatus(Pageable pageable, Integer emp, String projStatus);
	
	@Query("SELECT p.projCode FROM CreateProject p ORDER BY p.projCode DESC")
	CreateProject findByProjCode(Integer projCode);


}
