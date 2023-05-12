package com.greedy.moaware.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer>{
	
	@EntityGraph(attributePaths = {"job", "dept","fileCategory"})
	List<Emp> findAll();
	

}
