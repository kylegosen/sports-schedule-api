package com.gosenk.sports.schedule.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "venue")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Venue extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    // Other available properties
    // "geoCoordinates": null,
    // "capacitiesByEventType": [],
    // "playingSurface": null,
    // "baseballDimensions": [],
    // "hasRoof": null,
    // "hasRetractableRoof": null

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
