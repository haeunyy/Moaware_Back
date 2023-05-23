package com.greedy.moaware.schedule.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="SCH_PRARTICIPANT")
public class SchPrarticipant implements Serializable {

	@EmbeddedId
	private SchPrarPk schPrarPk;
	
}
