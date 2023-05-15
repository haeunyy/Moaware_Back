package com.greedy.moaware.work.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.entity.Work;
import com.greedy.moaware.work.entity.WorkPk;
import com.greedy.moaware.work.repository.WorkRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkService {
	
	private final WorkRepository workRepository;
	private final EmpRepository empRepository;
	private ModelMapper modelMapper;
	private final AuthEmpRepository authEmpRepository;
	
	public WorkService(WorkRepository workRepository, ModelMapper modelMapper, EmpRepository empRepository, AuthEmpRepository authEmpRepository) {
		this.modelMapper = modelMapper;
		this.workRepository = workRepository;
		this.empRepository = empRepository;
		this.authEmpRepository = authEmpRepository;
	}
	
	/* 자기 근태 현황 조회 */
	public Page<WorkDto> selectMyWorkList(Integer empCode, Date workDate, int page) {
		
		log.info("[WorkService] selectMyWorkList start ======================= ");
		
		log.info("[WorkService] empCode : {}", empCode);
		
		Optional<AuthEmp> empOptional = authEmpRepository.findById(empCode);
		AuthEmp emp = empOptional.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
		WorkPk workPk = new WorkPk();
		workPk.setEmpCode(empCode);
		
		// 해당 월의 첫 번째 날짜를 구합니다.
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(workDate);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    Date startDate = calendar.getTime();

	    // 해당 월의 마지막 날짜를 구합니다.
	    calendar.add(Calendar.MONTH, 1);
	    calendar.add(Calendar.DATE, -1);
	    Date endDate = calendar.getTime();
	    
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("workPk.workDate").descending());
	    Page<Work> workList = workRepository.findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(workPk.getEmpCode(), startDate, endDate, pageable);
	    workList.forEach(work -> Hibernate.initialize(work.getAuth().getRoleList()));

	    Page<WorkDto> workDtoList = workList.map(work -> modelMapper.map(work, WorkDto.class));
	    
		log.info("[WorkService] workDtoList.getContent() : {}", workDtoList.getContent());

		log.info("[WorkService] selectMyWorkList end ======================= ");
		
		return workDtoList;
	}
	
	/* 사원 번호 + 월별 근태 현황 조회 */
	public Page<WorkDto> selectWorkList(Integer empCode, Date workDate, int page) {
	    
		Optional<Emp> empOptional = empRepository.findById(empCode);
		Emp emp = empOptional.orElseThrow(() -> new UserNotFoundException("해당 사원이 없습니다."));
		
		
		// 해당 월의 첫 번째 날짜를 구합니다.
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(workDate);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    Date startDate = calendar.getTime();

	    // 해당 월의 마지막 날짜를 구합니다.
	    calendar.add(Calendar.MONTH, 1);
	    calendar.add(Calendar.DATE, -1);
	    Date endDate = calendar.getTime();
	    
// 		현재 월의 첫 날과 마지막 날 구하기
//	    LocalDate now = LocalDate.now();
//	    LocalDate firstDayOfMonth = now.withDayOfMonth(1);
//	    LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());

	    Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("workPk.workDate").descending());
	    Page<Work> workList = workRepository.findAllByWorkPkEmpCodeAndWorkPkWorkDateBetween(empCode, startDate, endDate, pageable);
	    Page<WorkDto> workDtoList = workList.map(work -> modelMapper.map(work, WorkDto.class));

	    return workDtoList;
	}
	
	/* 이름 + 날짜로 근무 조회 */

//    public Page<Work> findWorksByNameAndDate(String empName, LocalDate workDate, int page) {
//        
//    	Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("empCode").descending());
//    	Page<Emp> employee = empRepository.findByEmpName(empName, );
//
//        return workRepository.findByWorkPkEmpCodeAndWorkPkWorkDate(((Emp) employee).getEmpCode(), workDate, page);
//    }
}

