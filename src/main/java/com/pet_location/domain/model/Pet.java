package com.pet_location.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "PET")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long id;

    @Column(nullable = false, name = "id_sensor")
    private String sensorId;

    @Column(nullable = false, name = "num_latitude")
    private double latitude;

    @Column(nullable = false, name = "num_longitude")
    private double longitude;

    @Column(nullable = false, name = "dat_datetime")
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>();
}
