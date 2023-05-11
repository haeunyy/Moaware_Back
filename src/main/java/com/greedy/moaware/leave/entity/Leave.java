package com.greedy.moaware.leave.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="LEAVE")
public class Leave {

	@EmbeddedId
	private LeavePK employee;
	
	@Column(name="LEAVE_TOTAL_DAY")
	private String leaveTotalDay;

	@Column(name="LEAVE_USE_DAY")
	private String leaveUseDay;
	
}
