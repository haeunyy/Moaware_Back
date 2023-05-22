package com.greedy.moaware.schedule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.moaware.schedule.service.SchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/schedule")
public class SchController {

	private final SchService schService;

	public SchController(SchService schService) {
		this.schService = schService;
	}
	
}
