package com.greedy.moaware.review.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.greedy.moaware.employee.entity.FileCategory;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReviewEmpDto {

	private Integer empCode;
	private String empName;
	private String empID;
	private List<FileCategory> fileCategory;

}
