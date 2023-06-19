package com.practicalinterview.service;

import com.practicalinterview.datalayer.Transaction;
import com.practicalinterview.repository.TransactionRepository;
import com.practicalinterview.response.BaseResponse;
import com.practicalinterview.util.CustomerAccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Lazy
    @Autowired
    private CustomerAccountUtil customerAccountUtil;
    public BaseResponse findTransactionById(long transactionId) {
        try {
            Transaction transaction =  transactionRepository.findById(transactionId).orElse(null);
            if (transaction == null){
                throw new RuntimeException("Transaction not found!");
            }
            return new BaseResponse(transaction);
        }catch (Exception e){
            throw e;
        }
    }

    public BaseResponse getMiniStatement(long userId){
        try {
            return new BaseResponse(customerAccountUtil.getMiniStatement(userId));
        }catch (Exception e){
            throw e;
        }
    }

    public BaseResponse getAllTransactions() {
        try {
            return new BaseResponse(transactionRepository.findAll());
        }catch (Exception e){
            throw e;
        }
    }
}
