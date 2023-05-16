package com.greedy.moaware.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Integer>{


	
	
	
	@EntityGraph(attributePaths = {"job", "dept","fileCategory"})
	List<Emp> findAll();
	
//	@EntityGraph(attributePaths = {"job", "dept","fileCategory"})
//	List<Emp> findByRefDeptCode( Long refDetpCode);
	
	@Query("SELECT e FROM Emp e WHERE e.empName LIKE :empName% "
			+ " AND e.retireYn = 'N'")
	Page<Emp> findByEmpName(@Param("empName") String empName, Pageable pageable);
	

}
