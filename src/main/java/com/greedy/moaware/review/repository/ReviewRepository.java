package com.greedy.moaware.review.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.review.entity.TaskReview;

public interface ReviewRepository extends JpaRepository<TaskReview, Integer>{

	List<TaskReview> findByTaskAndStatus(Task task, String status);

}
