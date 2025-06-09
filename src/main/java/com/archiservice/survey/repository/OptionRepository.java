package com.archiservice.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.archiservice.survey.domain.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long>{

}
