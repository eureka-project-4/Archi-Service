package com.archiservice.survey.service;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.survey.dto.response.QuestionResponseDto;

import jakarta.servlet.http.HttpSession;

public interface SurveyService {
	ApiResponse<QuestionResponseDto> getQuestion(Long nextQuestionId, Long tagCode, HttpSession session);
	ApiResponse<String> saveResult(Long userId, HttpSession session);
}
