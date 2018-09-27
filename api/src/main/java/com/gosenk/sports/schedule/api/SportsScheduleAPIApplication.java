package com.gosenk.sports.schedule.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.gosenk.sports.schedule.common.repository"})
@ComponentScan("com.gosenk.sports.schedule")
@EntityScan(basePackages = {"com.gosenk.sports.schedule.common.entity"})
public class SportsScheduleAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportsScheduleAPIApplication.class, args);
    }
}