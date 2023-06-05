package com.greedy.moaware.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.greedy.moaware.employee.entity.Dept;
import com.greedy.moaware.employee.entity.Pdept;

public interface PdeptRepository extends JpaRepository<Pdept, Integer> {
	


}
