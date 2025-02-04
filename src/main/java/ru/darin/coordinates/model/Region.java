package ru.darin.coordinates.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Integer regionId;

    @NotEmpty(message = "Поле обязательно для заполнения")
    @Column(name = "region_name")
    @Getter
    @Setter
    private String regionName;

    @Column(name = "latitude")
    @Getter
    @Setter
    private Double latitude;

    @Column(name = "longitude")
    @Getter
    @Setter
    private Double longitude;
}
