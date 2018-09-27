package com.gosenk.sports.schedule.common.repository;

import com.gosenk.sports.schedule.common.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
}
