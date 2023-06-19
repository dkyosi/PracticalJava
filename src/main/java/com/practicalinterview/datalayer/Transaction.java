package com.practicalinterview.datalayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.practicalinterview.enums.TransactionTypes;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "transactions")
@Builder
public class Transaction extends Base {
    @Id
    @SequenceGenerator(name = "transactions_sequence_generator",
            sequenceName = "transactions_sequence_generator",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "transactions_sequence_generator")
    private long transactionId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionTypes transactionType;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    public Transaction(double amount, TransactionTypes transactionType, Account account) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
    }
}
