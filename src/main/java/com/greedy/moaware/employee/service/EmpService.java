package com.greedy.moaware.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.greedy.moaware.employee.dto.EmpDto;
import com.greedy.moaware.employee.entity.Emp;
import com.greedy.moaware.employee.repository.DeptRepository;
import com.greedy.moaware.employee.repository.EmpRepository;
import com.greedy.moaware.organization.dto.OrganizationDto;
import com.greedy.moaware.organization.entity.Organization;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmpService {
	
	private final EmpRepository empRepository;
	private final DeptRepository deptRepository;
	private final ModelMapper modelMapper;
	
	@Value("${image.image-url}")
	private String IMAGE_URL;
	
	public EmpService( EmpRepository empRepository, ModelMapper modelMapper
			, DeptRepository deptRepository) {
		this.empRepository = empRepository;
		this.deptRepository = deptRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<EmpDto> selectEmpList() {
		
		log.info("[EmpService] selectEmpList start ============================== ");
		
		List<Emp> empList = empRepository.findAll();
		
		log.info("{}", empList.get(0).getDept());
		
		List<EmpDto>empDtoList = empList.stream().map( emp -> modelMapper.map(emp, EmpDto.class)).collect(Collectors.toList());
		
		
		log.info("[EmpService] selectEmpList end ================================ ");
		
		return empDtoList;
		
	}

	public EmpDto selectEmpDetail(Integer empCode) {
		log.info("[EmpService] selectOrgDetail start ============================== ");
		log.info("[EmpService] empCode : {}" , empCode);
		
		Emp empList = empRepository.findById(empCode)
				.orElseThrow( ()-> new IllegalArgumentException("해당 사번을 가진 사원이 없습니다. 사번 = " + empCode));
		log.info("[EmpService] empList : {}" , empList);
		
		EmpDto empDto = modelMapper.map(empList, EmpDto.class);
		log.info("[EmpService] empDto : {}" , empDto);
		
		log.info("[EmpService] selectOrgDetail end ================================ ");
		return empDto;
		
	}
	

	

}
