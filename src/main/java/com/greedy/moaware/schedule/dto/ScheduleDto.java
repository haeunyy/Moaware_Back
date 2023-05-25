package com.greedy.moaware.schedule.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.greedy.moaware.schedule.entity.SchCategory;
import com.greedy.moaware.schedule.entity.SchPrarticipant;

import lombok.Data;

@Data
public class ScheduleDto {

	private Integer schCode;

	private String schName;

	private String schContent;

	private Date schDate;

	private Date schEndDate;

	private Integer schAuthor;

	private List<SchPrarticipantDto> schPrarticipant;

	private SchCategoryDto schType;

	
}
