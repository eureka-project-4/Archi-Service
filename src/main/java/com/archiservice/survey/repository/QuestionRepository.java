package com.archiservice.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.archiservice.survey.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
}
