package com.pet_location.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "LOCATION")
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_location")
    private Long id;

    @Column(name = "nam_country")
    private String country;

    @Column(name = "nam_state")
    private String state;

    @Column(name = "nam_city")
    private String city;

    @Column(name = "nam_neighborhood")
    private String neighborhood;

    @Column(name = "nam_address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
    private Pet pet;
}