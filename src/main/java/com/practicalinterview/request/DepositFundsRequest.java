package com.practicalinterview.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositFundsRequest {
    private long userId;
    private double amount;
}
