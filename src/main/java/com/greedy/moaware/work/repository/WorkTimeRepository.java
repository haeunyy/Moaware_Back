package com.greedy.moaware.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.work.entity.WorkPk;
import com.greedy.moaware.work.entity.WorkTime;

public interface WorkTimeRepository extends JpaRepository<WorkTime, WorkPk>{

}
