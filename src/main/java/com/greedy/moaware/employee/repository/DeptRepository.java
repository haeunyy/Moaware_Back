package com.greedy.moaware.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.employee.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept, Integer>{

}
