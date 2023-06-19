package com.practicalinterview.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawFundsRequest {
    private double amount;
    private long userId;
}
