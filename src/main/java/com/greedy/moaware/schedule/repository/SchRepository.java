package com.greedy.moaware.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.schedule.entity.Schedule;

public interface SchRepository extends JpaRepository<Schedule, Integer> {

}
