package com.greedy.moaware.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
							
	@EntityGraph(attributePaths= {"orgEmp", "orgEmp.job"})
	List<Organization> findAll();
}		
