package com.greedy.moaware.organization.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.moaware.organization.dto.OrganizationDto;
import com.greedy.moaware.organization.entity.Organization;
import com.greedy.moaware.organization.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class OrganizationService {
	
	private final OrganizationRepository organizationRepository;
	private final ModelMapper modelMapper;
	
	public OrganizationService( OrganizationRepository organizationRepository, ModelMapper modelMapper) {
		this.organizationRepository = organizationRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<OrganizationDto> selectOranizationList() {
		
		log.info("[OrganizationService] selectOranizationList start ============================== ");
		
		List<Organization> orgList = organizationRepository.findAll();
		
		List<OrganizationDto> orgDtoList = orgList.stream().map( org -> modelMapper.map(org, OrganizationDto.class)).collect(Collectors.toList());
		
		log.info("[OrganizationService] selectOranizationList start ============================== ");
		
		return orgDtoList;
	}

}
