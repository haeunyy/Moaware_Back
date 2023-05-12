package com.greedy.moaware.organization.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.greedy.moaware.organization.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
							
	@EntityGraph(attributePaths= {"orgEmp", "orgEmp.job"})
	List<Organization> findAll();

	@Query(value="SELECT DISTINCT o FROM Organization o JOIN fetch o.orgEmp e JOIN fetch e.job j "
			+ "WHERE o.deptName like %:search% or e.empName like %:search% or j.jobName like %:search%"
			)
	List<Organization> findBySearch(@Param("search") String search);

}		
