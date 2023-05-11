package com.greedy.moaware.employee.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptDto {
	
	private Integer deptCode;
	private String deptName;
	private String refDeptCode;
	private DeptDto highDept;
	private List<DeptDto> lowDept;
	
	
	public DeptDto() {}
	

	public DeptDto(Integer deptCode, String deptName, String refDeptCode, DeptDto highDept, List<DeptDto> lowDept) {
		super();
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.refDeptCode = refDeptCode;
		this.highDept = highDept;
		this.lowDept = lowDept;
	}

}
