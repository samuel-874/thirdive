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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UpcomingEvent {
    @SequenceGenerator(
            name = "upcoming_events_seq",
            sequenceName = "upcoming_events_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "upcoming_events_seq")
    private long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Vehicle vehicle;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "history_id")
    @JsonIgnore
    private History history;
    private String pickOffLocation;
    private String dropOffLocation;
    private String distanceInString;
    private String durationInString;
    private int distanceInInt;
    private int durationInInt;
    // 2023-06-15
    private LocalDate date;
    // hh:mm:ss:msms
    private LocalTime time;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private UserEntity customer;
}
