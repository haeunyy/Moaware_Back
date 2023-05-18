package com.greedy.moaware.work.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import org.springframework.transaction.annotation.Transactional;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.exception.NotLogin;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.work.dto.WorkDto;
import com.greedy.moaware.work.dto.WorkTimeDto;
import com.greedy.moaware.work.entity.Work;
import com.greedy.moaware.work.entity.WorkPk;
import com.greedy.moaware.work.entity.WorkTime;
import com.greedy.moaware.work.repository.WorkRepository;
import com.greedy.moaware.work.repository.WorkTimeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkService {
	
	private final WorkRepository workRepository;
	private final WorkTimeRepository workTimeRepository;
	private final EmpRepository empRepository;
	private ModelMapper modelMapper;
	private final AuthEmpRepository authEmpRepository;
	
	public WorkService(WorkRepository workRepository, ModelMapper modelMapper, EmpRepository empRepository, AuthEmpRepository authEmpRepository,
			WorkTimeRepository workTimeRepository) {
		this.modelMapper = modelMapper;
		this.workRepository = workRepository;
		this.empRepository = empRepository;
		this.authEmpRepository = authEmpRepository;
		this.workTimeRepository = workTimeRepository;
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
	
	/* 시간 insert */
	@Transactional
	public void insertStart(WorkTimeDto workTimeDto) {
		
		log.info("[WorkService] insertStart start ======================= ");	
		log.info("[WorkService] workTimeDto : {}", workTimeDto);
		log.info("[WorkService] empCode : {}", workTimeDto.getWorkPk().getEmpCode());
		log.info("[WorkService] authEmpRepository : {}", authEmpRepository.findById(workTimeDto.getWorkPk().getEmpCode()));
		log.info("[WorkService] workTimeDto.workpK.workDate : {}", workTimeDto.getWorkPk().getWorkDate());
		//findById 는 반환 타입이 optional<T>로 지정 되어있다.

		Optional<AuthEmp> authEmpOptional = authEmpRepository.findById(workTimeDto.getWorkPk().getEmpCode());
		
		if (authEmpOptional.isPresent()) {
		    AuthEmp authEmp = authEmpOptional.get();
		    
//		    Date workTime = workTimeDto.getWorkTime();
		    
		    LocalDateTime nowTime = LocalDateTime.now();
		    
		    // LocalDateTime을 ZonedDateTime으로 변환 (시간대 정보 추가)
	        ZonedDateTime zonedDateTime = nowTime.atZone(ZoneId.systemDefault());

	        // ZonedDateTime을 Instant로 변환
	        Instant instant = zonedDateTime.toInstant();

	        // Instant를 Date로 변환
	        Date date = Date.from(instant);
	        
		    //현재 년도와 월과 일은 고정으로 하고 출근 시간만 고정
		    LocalDateTime fixedStartTime = nowTime.withHour(9).withMinute(0).withSecond(0).withNano(0);
		    LocalDateTime fixedAfterTime = nowTime.withHour(13).withMinute(0).withSecond(0).withNano(0);
		    
		    
		    
		    //이전 시간
		    if(nowTime.isBefore(fixedStartTime)) {
		    	workTimeDto.setWorkStatus("정상출근");
		    
		    	//이후 시간
		    } else if (nowTime.isAfter(fixedAfterTime)) {
		    	workTimeDto.setWorkStatus("지각");
		    } else {
		    	//사이 시간
		    	workTimeDto.setWorkStatus("지각");
		    }
		    
		    workTimeDto.setWorkTime(date);
		    workTimeDto.setQuitTime(null);
		    workTimeRepository.save(modelMapper.map(workTimeDto, WorkTime.class));
		} else {
			throw new NotLogin("로그인이 되어 있지 않습니다.");
		}
		
		
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

