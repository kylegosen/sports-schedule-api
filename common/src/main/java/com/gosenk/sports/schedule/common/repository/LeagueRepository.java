package com.gosenk.sports.schedule.common.repository;

import com.gosenk.sports.schedule.common.entity.League;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends CrudRepository<League, String> {
}
