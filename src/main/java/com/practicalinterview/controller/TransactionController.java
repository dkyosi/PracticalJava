package com.practicalinterview.controller;

import com.practicalinterview.response.BaseResponse;
import com.practicalinterview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{transactionId}")
    public BaseResponse searchTransactionById(@PathVariable("transactionId") long transactionId){
        try {
            return transactionService.findTransactionById(transactionId);
        }catch (Exception e){
            return new BaseResponse("Failed to fetch transaction");
        }
    }

    @GetMapping("/statement/{userId}")
    public BaseResponse getMiniStatement(@PathVariable("userId") long userId){
        try {
            return transactionService.getMiniStatement(userId);
        }catch (Exception e){
            return new BaseResponse("Failed to fetch statement");
        }
    }

    @GetMapping
    public BaseResponse getAllTransactions(){
        try {
            return transactionService.getAllTransactions();
        }catch (Exception e){
            return new BaseResponse("Failed to fetch transactions");
        }
    }
}
