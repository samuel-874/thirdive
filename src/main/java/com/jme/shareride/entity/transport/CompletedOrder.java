package com.jme.shareride.entity.transport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CompletedOrder {
    @SequenceGenerator(
            name = "completed_events_seq",
            sequenceName = "completed_events_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "completed_events_seq")
    private long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Vehicle orderedVehicle;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "history_id")
    @JsonIgnore
    private History history;
    private String pickOffLocation;
    private String dropOffLocation;
    private String distance;
    private String duration;
    // 2023-06-15
    private LocalDate date;
    // hh:mm:ss:msms
    private LocalTime time;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private UserEntity customer;
    private int charge;
    private int vat;
    private int total;
}
