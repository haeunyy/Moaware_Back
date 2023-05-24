package com.greedy.moaware.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.schedule.entity.Schedule;

public interface SchRepository extends JpaRepository<Schedule, Integer> {

	@EntityGraph(attributePaths= {"schType"})
	List<Schedule> findBySchAuthor(Integer emp);

}
