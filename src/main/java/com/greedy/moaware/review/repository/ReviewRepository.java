package com.greedy.moaware.review.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.greedy.moaware.project.entity.Task;
import com.greedy.moaware.review.entity.TaskReview;

public interface ReviewRepository extends JpaRepository<TaskReview, Integer>{

	@EntityGraph(attributePaths= {"task"})
	Slice<TaskReview> findByTaskAndStatus(Pageable page, Task task, String status);


}
