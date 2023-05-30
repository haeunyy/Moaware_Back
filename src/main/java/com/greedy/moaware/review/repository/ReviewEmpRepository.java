package com.greedy.moaware.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.review.entity.ReviewEmp;

public interface ReviewEmpRepository extends JpaRepository<ReviewEmp, Integer>{
	

}