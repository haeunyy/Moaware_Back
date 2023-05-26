package com.greedy.moaware.leave.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.leave.entity.LeavePayment;

public interface LeavePaymentRepository extends JpaRepository<LeavePayment, Integer> {
	
	@Query("SELECT l " +
		       "FROM LeavePayment l " +
		       "WHERE l.employee.empCode = :empCode " +
		       "AND l.lPayStatus IN (:lPayStatusList)")
	Page<LeavePayment> findByEmployeeEmpCodeAndlPayStatus(@Param("empCode") Integer empCode, @Param("lPayStatusList") List<String> lPayStatusList, Pageable pageable);
	
	@Query("SELECT l " +
		       "FROM LeavePayment l " +
		       "WHERE l.employee.empCode = :empCode " +
		       "AND l.lPayStatus IN (:lPayStatusList)" + 
		       "AND l.leaveReqDate >= :startDate " +
		       "AND l.leaveReqDate <= :endDate")
	Page<LeavePayment> findByEmployeeEmpCodeAndlPayStatusAndLeaveReqDate(@Param("empCode") Integer empCode, @Param("lPayStatusList") List<String> asList,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
	
}
