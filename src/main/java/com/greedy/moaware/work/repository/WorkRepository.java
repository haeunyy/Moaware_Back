package com.greedy.moaware.work.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.work.entity.Work;
import com.greedy.moaware.work.entity.WorkPk;

public interface WorkRepository extends JpaRepository<Work, WorkPk> {

	@EntityGraph(attributePaths = { "workPk" })
	Page<Work> findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(Integer empCode, Date startDate, Date endDate,
			Pageable pageable);

//	Page<Work> findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(Integer empCode, LocalDate firstDayOfMonth,
//			LocalDate lastDayOfMonth, Pageable pageable);
	

}
