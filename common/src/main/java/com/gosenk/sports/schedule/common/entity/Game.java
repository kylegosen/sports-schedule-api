package com.gosenk.sports.schedule.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game extends BaseEntity {

    @Column(name = "week")
    private long week;

    @Column(name = "start_time")
    private Date startTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "venue_allegiance")
    private String venueAllegiance;

    @Column(name = "schedule_status")
    private String scheduleStatus;

    @Column(name = "original_start_time")
    private Date originalStartTime;

    @Column(name = "delayed_or_postponed_reason")
    private String delayedOrPostponedReason;

    @Column(name = "played_status")
    private String playedStatus;


    // Other available properties
    // "endedTime": null,
    // "attendance": null,
    // "officials": [],
    // "broadcasters": [],
    // "weather": null

    public long getWeek() {
        return week;
    }

    public void setWeek(long week) {
        this.week = week;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getVenueAllegiance() {
        return venueAllegiance;
    }

    public void setVenueAllegiance(String venueAllegiance) {
        this.venueAllegiance = venueAllegiance;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Date getOriginalStartTime() {
        return originalStartTime;
    }

    public void setOriginalStartTime(Date originalStartTime) {
        this.originalStartTime = originalStartTime;
    }

    public String getDelayedOrPostponedReason() {
        return delayedOrPostponedReason;
    }

    public void setDelayedOrPostponedReason(String delayedOrPostponedReason) {
        this.delayedOrPostponedReason = delayedOrPostponedReason;
    }

    public String getPlayedStatus() {
        return playedStatus;
    }

    public void setPlayedStatus(String playedStatus) {
        this.playedStatus = playedStatus;
    }

    public long getHomeTeamId(){
        return getHomeTeam().getId();
    }

    public long getAwayTeamId(){
        return getAwayTeam().getId();
    }
}
