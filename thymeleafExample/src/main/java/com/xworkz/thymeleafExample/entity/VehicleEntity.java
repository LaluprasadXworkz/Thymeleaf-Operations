package com.xworkz.thymeleafExample.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class VehicleEntity {
    @Id
    @Column(name = "v_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "v_Name")
    @NonNull
    private String vehicleName;

    @Column(name = "Num_plate")
    @NonNull
    private String numberPlate;

    @Column(name = "owner_Name")
    @NonNull
    private String ownerName;

    @Column(name = "NumOfWheel")
    @NonNull
    private int numberOfWheel;

    @Column(name = "mfd")
    @NonNull
    private LocalDate mfDate;
}
