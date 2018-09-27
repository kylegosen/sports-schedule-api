package com.gosenk.sports.schedule.data;

import com.gosenk.sports.schedule.data.processor.MLBProcessor;
import com.gosenk.sports.schedule.data.processor.NBAProcessor;
import com.gosenk.sports.schedule.data.processor.NFLProcessor;
import com.gosenk.sports.schedule.data.processor.NHLProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.gosenk.sports.schedule.common.repository"})
@ComponentScan("com.gosenk.sports.schedule")
@EntityScan(basePackages = {"com.gosenk.sports.schedule.common.entity"})
public class SportsScheduleDataApplication implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SportsScheduleDataApplication.class, args);
    }

    @Autowired
    private NFLProcessor nflProcessor;

    @Autowired
    private MLBProcessor mlbProcessor;

    @Autowired
    private NBAProcessor nbaProcessor;

    @Autowired
    private NHLProcessor nhlProcessor;

    @Override
    public void run(String... args) throws Exception {
        // TODO - Parameterize

        nflProcessor.process();
        mlbProcessor.process();
        nbaProcessor.process();
        nhlProcessor.process();
    }
}
