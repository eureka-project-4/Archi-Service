package com.archiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class}) // 테스트할때만 붙임
@EnableJpaAuditing
public class ArchiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ArchiServiceApplication.class, args);
  }

}
