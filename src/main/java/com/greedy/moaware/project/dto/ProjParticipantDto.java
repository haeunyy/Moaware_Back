package com.greedy.moaware.project.dto;

import lombok.Data;

@Data
public class ProjParticipantDto {
	
	private ProjParticipantPkDto projMember;
	private ProjEmpDto emp;
	
    public Integer getProjMember() {
        return projMember.getProjMember();
    }

}
