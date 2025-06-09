package com.archiservice.survey.service;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.survey.dto.response.QuestionResponseDto;

public interface SurveyService {
	ApiResponse<QuestionResponseDto> getQuestion(Long nextQuestionId);
}
