package com.greedy.moaware.schedule.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class SchPrarPk implements Serializable {

	@Column(name="SCH_CODE")
	private Integer schCode;
	
	@Column(name="EMP_CODE")
	private Integer schMember;

	public SchPrarPk(Integer schCode, Integer schMember) {
		super();
		this.schCode = schCode;
		this.schMember = schMember;
	}
	
	
	
}
