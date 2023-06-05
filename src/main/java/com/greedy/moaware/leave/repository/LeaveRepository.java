package com.greedy.moaware.leave.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.leave.entity.Leave;
import com.greedy.moaware.leave.entity.LeavePk;

public interface LeaveRepository extends JpaRepository<Leave, LeavePk>{

	@Query("SELECT l " + 
			   "  FROM Leave l " +   
			   " WHERE l.leavePk.empCode = :empCode" + " AND l.leavePk.leaveYear = :leaveYear")
	Optional<Leave> findByLeavePkEmpCodeAndLeavePkLeaveYear(@Param("empCode") Integer empCode, @Param("leaveYear") Integer leaveYear);

}
