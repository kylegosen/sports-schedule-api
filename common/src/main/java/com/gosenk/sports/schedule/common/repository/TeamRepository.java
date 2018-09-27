package com.gosenk.sports.schedule.common.repository;

import com.gosenk.sports.schedule.common.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
}
