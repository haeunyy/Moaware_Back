package com.greedy.moaware.schedule.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.entity.AuthEmp;
import com.greedy.moaware.employee.repository.AuthEmpRepository;
import com.greedy.moaware.exception.UserNotFoundException;
import com.greedy.moaware.schedule.entity.SchPrarticipant;
import com.greedy.moaware.schedule.entity.Schedule;
import com.greedy.moaware.schedule.repository.SchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchService {

	private final SchRepository schRepository;
	private final AuthEmpRepository authEmpRepository;
	private ModelMapper modelMapper;
	
	public SchService(SchRepository schRepository, AuthEmpRepository authEmpRepository, ModelMapper modelMapper) {
		this.schRepository = schRepository;
		this.authEmpRepository = authEmpRepository;
		this.modelMapper = modelMapper;
	}

	public List<Schedule> getScheduleListByUser(Integer emp) {
		
		AuthEmp employee = authEmpRepository.findById(emp)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + emp));
		log.info(" getScheduleListByUser : emp {} " , emp);
		//List<Schedule> schedules = schRepository.findAll();
		Integer a = 6;
		
		List<Schedule> schedules = schRepository.findBySchAuthor(a);
		
		log.info(" getScheduleListByUser : {} " , schedules);
		
		return null;
	}
}
