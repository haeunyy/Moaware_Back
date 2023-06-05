package com.greedy.moaware.schedule.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.schedule.entity.SchPrarticipant;
import com.greedy.moaware.schedule.entity.Schedule;

public interface SchRepository extends JpaRepository<Schedule, Integer> {
	
	List<Schedule> findBySchAuthor(Emp employee);
	
	Optional<Schedule> findBySchCodeAndSchAuthor(Integer schCode, Emp employee);
	
}
