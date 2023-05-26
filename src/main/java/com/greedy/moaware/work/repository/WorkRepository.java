package com.greedy.moaware.work.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.work.dto.WorkTimeDto;
import com.greedy.moaware.work.entity.Work;
import com.greedy.moaware.work.entity.WorkPk;

public interface WorkRepository extends JpaRepository<Work, WorkPk> {

//	@EntityGraph(attributePaths = { "workPk", "auth.roleList" })
	Page<Work> findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(Integer emp, Date startDate, Date endDate,
	
			Pageable pageable);
	
	Page<Work> findByWorkPkWorkDate(Date workDate, Pageable pageable);
	
//	@EntityGraph(attributePaths = {"workPk", "auth.roleList"})
//	Page<Work> findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(Integer emp, Date startDate, Date endDate, Pageable pageable);

//	Page<Work> findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(Integer empCode, LocalDate firstDayOfMonth,
//			LocalDate lastDayOfMonth, Pageable pageable);
	

}
