package com.greedy.moaware.payment.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "REFENCE_MEMBER")
public class RefenceMember {
	
	@EmbeddedId
	private RefenceMemberPk refenceMemberPk;

}
	