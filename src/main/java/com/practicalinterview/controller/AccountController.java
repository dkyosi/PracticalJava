package com.practicalinterview.controller;

import com.practicalinterview.request.CreateAccountRequest;
import com.practicalinterview.request.DepositFundsRequest;
import com.practicalinterview.request.TransferFundsRequest;
import com.practicalinterview.request.WithdrawFundsRequest;
import com.practicalinterview.response.BaseResponse;
import com.practicalinterview.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public BaseResponse getAllAccounts(){
        try {
            return accountService.getAllAccounts();
        }catch (Exception e){
            return new BaseResponse(400,"Failed to get accounts "+e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public BaseResponse depositFunds(@RequestBody DepositFundsRequest request){
        try {
            return accountService.depositToAccount(request);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to transfer funds "+e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public BaseResponse withdrawFunds(@RequestBody WithdrawFundsRequest request){
        try {
            return accountService.withdrawFromAccount(request);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to transfer funds "+e.getMessage());
        }
    }

    @GetMapping("/account/{userId}")
    public BaseResponse findAccount(@PathVariable("userId") long userId){
        try {
            return accountService.getAccount(userId);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to get account "+e.getMessage());
        }
    }

    @GetMapping("/balance/{userId}")
    public BaseResponse getAccountBalance(@PathVariable("userId") long userId){
        try {
            return accountService.getAccountBalance(userId);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to get balance "+e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public BaseResponse transferFunds(@RequestBody TransferFundsRequest request){
        try {
            return accountService.transferFindsToAnotherAccount(request);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to transfer funds : "+e.getMessage());
        }
    }

    @PostMapping("/create")
    public BaseResponse createAccount(@RequestBody CreateAccountRequest request){
        try {
            return accountService.createAccount(request);
        }catch (Exception e){
            return new BaseResponse(400,"Failed to create account "+e.getMessage());
        }
    }
}
