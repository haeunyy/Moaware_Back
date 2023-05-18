package com.greedy.moaware.project.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import com.greedy.moaware.project.entity.Project;
import com.greedy.moaware.project.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

	List<Task> findByProjectAndStatus(Integer projCode, String string, Sort sort);

	Streamable<Order> findByProject(Integer projCode);

}
