package com.archiservice.survey.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.survey.dto.response.QuestionResponseDto;
import com.archiservice.survey.service.SurveyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SuerveyController {

	private final SurveyService surveyService;
	
	@GetMapping("/questions")
	public ResponseEntity<ApiResponse<QuestionResponseDto>> getQuestion(
			@RequestParam(required = false, name = "nextQuestionId") Long nextQuestionId){
		return ResponseEntity.ok(surveyService.getQuestion(nextQuestionId));
	}
	
}
