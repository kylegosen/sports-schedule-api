package com.gosenk.sports.schedule.common.repository;

import com.gosenk.sports.schedule.common.entity.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends CrudRepository<Venue, Long> {
}
