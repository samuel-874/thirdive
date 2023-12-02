package com.jme.shareride.entity.user_and_auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @SequenceGenerator(
            name = "wallet_seq",
            sequenceName = "wallet_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "wallet_seq")
    private long id;
    private int balance;
    private int totalExpend=0;
    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;
    @OneToOne
    @JsonIgnore
    private UserEntity user;
    private LocalDateTime dateTime =LocalDateTime.now();
}
