package com.greedy.moaware.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer>{

	Optional<Emp> findByEmpId(String empId);
	
	
	
	

}
