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
	
//	@Query("SELECT l, p " +
//		       "FROM Leave l " + 
//		       " JOIN LeavePayment p " + "ON(l.leavePk.empCode = p.employee.empCode)" +
//		       "WHERE p.employee.empCode = :empCode " +
//		       "AND p.lPayStatus IN (:lPayStatusList) " +
//		       "AND p.leaveReqDate BETWEEN :startDate AND :endDate")
	
//	@Query("SELECT p FROM LeavePayment p JOIN fetch p.leave l JOIN fetch l.leavePk k " + 
//				"WHERE p.employee.empCode = :empCode" + "AND p.lpayStatus IN (:lPayStatusList) " + 
//				"AND p.leaveReqDate BETWEEN :startDate AND :endDate" )
//	Page<LeavePayment> findByEmployeeEmpCodeAndlPayStatusAndLeaveReqDateBetween(@Param("empCode") Integer empCode, @Param("lPayStatusList") List<String> asList, @Param("startDate") Date startDate,
//			@Param("endDate") Date endDate, Pageable pageable);
	
//	@Query(value = "SELECT p " +
//            "FROM LEAVE_PAYMENT p " +
//            "JOIN LEAVE l ON p.LEAVE_CODE = l.LEAVE_CODE " +
//            "JOIN LEAVE_PK k ON l.LEAVE_CODE = k.LEAVE_CODE " +
//            "WHERE p.EMP_CODE = :empCode " +
//            "AND p.L_PAY_STATUS IN (:lPayStatusList) " +
//            "AND p.LEAVE_REQ_DATE BETWEEN :startDate AND :endDate",
//    nativeQuery = true)
//		Page<LeavePayment> findByEmployeeEmpCodeAndlPayStatusAndLeaveReqDateBetween(
//		        @Param("empCode") Integer empCode,
//		        @Param("lPayStatusList") List<String> lPayStatusList,
//		        @Param("startDate") Date startDate,
//		        @Param("endDate") Date endDate,
//		        Pageable pageable);
//	
//	@Query("SELECT l " +
//		       "FROM LeavePayment l " +
//		       "WHERE l.emplyoee.empCode = :empCode " +
//		       "AND l.lPayStatus = :lPayStatus")
//	Page<LeavePayment> findByEmployeeEmpCodeAndlPayStatus(@Param("empCode") Integer empCode, @Param("lPayStatus") String lPayStatus, Pageable pageable);

}
