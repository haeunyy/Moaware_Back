package com.greedy.moaware.schedule.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.schedule.repository.SchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchService {

	private final SchRepository schRepository;
	private final EmpRepository empRepository;
	private ModelMapper modelMapper;
	
	public SchService(SchRepository schRepository, EmpRepository empRepository, ModelMapper modelMapper) {
		super();
		this.schRepository = schRepository;
		this.empRepository = empRepository;
		this.modelMapper = modelMapper;
	}
	
}
