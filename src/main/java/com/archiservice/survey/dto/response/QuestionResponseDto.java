package com.archiservice.survey.dto.response;

import java.util.List;

import com.archiservice.survey.domain.Question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {
	String questionText;
	int order;
	List<OptionResponseDto> options;
	
	public static QuestionResponseDto from(Question question) {
		return QuestionResponseDto.builder()
				.questionText(question.getQuestionText())
				.order(question.getQuestionOrder())
				.options(question.getOptions().stream().map(OptionResponseDto::from).toList())
				.build();
	}
}
