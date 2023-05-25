package com.greedy.moaware.leave.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.leave.entity.Leave;
import com.greedy.moaware.leave.entity.LeavePk;

public interface LeaveRepository extends JpaRepository<Leave, LeavePk>{
//	@Query("SELECT l " +
//		       "FROM Leave l " +
//		       "WHERE l.leavePayment.employee.empCode = :empCode " +
//		       "AND l.leavePayment.lPayStatus IN (:lPayStatusList)" + "AND l.leavePk.leaveYear = :leaveYear ")
//	Page<Leave> findByLeavePkEmpCodeAndLeavePkLeaveYearAndLeavePaymentLPayStatus(@Param("empCode") Integer empCode, @Param("leaveYear") Integer leaveYear,
//			@Param("lPayStatusList") List<String> asList, Pageable pageable);

//	Page<Leave> findByLeavePkEmpCodeAndLeavePkLeaveYear(Integer empCode, Integer leaveYear, Pageable pageable);

}
