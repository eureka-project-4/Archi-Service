package com.archiservice.survey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.archiservice.common.response.ApiResponse;
import com.archiservice.exception.BusinessException;
import com.archiservice.exception.ErrorCode;
import com.archiservice.survey.domain.Question;
import com.archiservice.survey.dto.response.QuestionResponseDto;
import com.archiservice.survey.repository.QuestionRepository;
import com.archiservice.survey.service.SurveyService;
import com.archiservice.user.domain.User;
import com.archiservice.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService{

	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	
	@Override
	public ApiResponse<QuestionResponseDto> getQuestion(Long nextQuestionId, Long tagCode, HttpSession session) {
		
		if (Long.valueOf(1L).equals(nextQuestionId)) {
		    session.removeAttribute("tagCodes");
		}
		
	    List<Long> tagCodes = (List<Long>) session.getAttribute("tagCodes");

	    if (tagCodes == null) {
	        tagCodes = new ArrayList<>();
	        session.setAttribute("tagCodes", tagCodes);
	    }

	    if (tagCode != null) {
	        tagCodes.add(tagCode);
	    }
		
		Question question;
		
		if (nextQuestionId == null) {
			// 성향 테스트 종료 지점
			tagCodes.add(tagCode);
			return ApiResponse.success(new QuestionResponseDto("성향 테스트 종료", 0, List.of()));
			
		}else {
			question = questionRepository.findById(nextQuestionId)
					.orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "문항이 존재하지 않습니다."));
		}
		
		QuestionResponseDto questionResponseDto = QuestionResponseDto.from(question);
		
		return ApiResponse.success(questionResponseDto);
	}

	@Override
	public ApiResponse<String> saveResult(Long userId, HttpSession session) {
		User user = userRepository.findById(userId)
		        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		
		List<Long> tagCodes = (List<Long>) session.getAttribute("tagCodes"); // List
		
		Long tagCode = tagCodes.stream().mapToLong(Long::longValue).sum();
		user.setTagCode(tagCode);
		userRepository.save(user);
		return ApiResponse.success("성향 저장");
	}
	
	
}
