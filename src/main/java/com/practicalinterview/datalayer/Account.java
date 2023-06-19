package com.practicalinterview.datalayer;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "accounts")
@Builder
public class Account extends Base{
    @Id
    @SequenceGenerator(name = "account_sequence_generator",
            sequenceName = "account_sequence_generator",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_sequence_generator")
    private long accountId;
    @OneToOne
    private Customer customer;
    private double balance;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    public Account(Customer customer, double balance) {
        this.customer = customer;
        this.balance = balance;
    }
}
