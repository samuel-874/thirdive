package com.jme.shareride.entity.transport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jme.shareride.entity.user_and_auth.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class History {
    @SequenceGenerator(
            name = "history_sequence",
            sequenceName = "history_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "history_sequence")
    private long id;
    @OneToMany(mappedBy = "history",cascade = CascadeType.REMOVE)
    private List<UpcomingEvent> upcomingEventEvents;
    @OneToMany(mappedBy = "history",cascade = CascadeType.REMOVE)
    private List<CompletedOrder> completedOrderEvents;
    @OneToMany(mappedBy = "history",cascade = CascadeType.REMOVE)
    private List<Cancelled> cancelledEvents;
    @OneToOne
    @JsonIgnore
    private UserEntity user;
}
