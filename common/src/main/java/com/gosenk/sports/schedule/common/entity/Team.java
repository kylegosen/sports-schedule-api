package com.gosenk.sports.schedule.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gosenk.sports.schedule.common.comparator.GameComparator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "team")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team extends BaseEntity {

    @Column(name = "city")
    private String city;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue homeVenue;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "homeTeam")
    private Set<Game> homeGames = new HashSet<>(0);

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "awayTeam")
    private Set<Game> awayGames = new HashSet<>(0);

    // Other properties available
    //"teamColoursHex": [],
    //"socialMediaAccounts": [],
    //"officialLogoImageSrc": null

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Venue getHomeVenue() {
        return homeVenue;
    }

    public void setHomeVenue(Venue homeVenue) {
        this.homeVenue = homeVenue;
    }

    // Transient
    public String getImage(){
        return getLeague().getId().toLowerCase() + "_" + getAbbreviation().toLowerCase();
    }

    public String getLeagueId(){
        return getLeague().getId();
    }

    public Set<Game> getHomeGames() {
        return homeGames;
    }

    public void setHomeGames(Set<Game> homeGames) {
        this.homeGames = homeGames;
    }

    public Set<Game> getAwayGames() {
        return awayGames;
    }

    public void setAwayGames(Set<Game> awayGames) {
        this.awayGames = awayGames;
    }

    @JsonProperty("games")
    public List<Game> getAllGames(){
        List<Game> results = new ArrayList<>();
        results.addAll(getHomeGames());
        results.addAll(getAwayGames());
        results.sort(new GameComparator());
        return results;
    }
}
