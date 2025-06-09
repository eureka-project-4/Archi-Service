package com.archiservice.survey.service.impl;

import org.springframework.stereotype.Service;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.survey.domain.Question;
import com.archiservice.survey.dto.response.QuestionResponseDto;
import com.archiservice.survey.repository.QuestionRepository;
import com.archiservice.survey.service.SurveyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService{

	private final QuestionRepository questionRepository;
	
	@Override
	public ApiResponse<QuestionResponseDto> getQuestion(Long nextQuestionId) {
		
		Question question;
		
		if (nextQuestionId == null) {
			// 성향 테스트 종료 지점
			return ApiResponse.success(null);
			
			// to do : 성향 테스트 응답 결과 api
			
		}else {
			question = questionRepository.findById(nextQuestionId)
					.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "문항이 존재하지 않습니다."));
		}
		
		QuestionResponseDto questionResponseDto = QuestionResponseDto.from(question);
		
		return ApiResponse.success(questionResponseDto);
	}

	
}
