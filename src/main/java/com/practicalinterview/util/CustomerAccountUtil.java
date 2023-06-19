package com.practicalinterview.util;

import com.practicalinterview.datalayer.Account;
import com.practicalinterview.datalayer.Customer;
import com.practicalinterview.datalayer.Transaction;
import com.practicalinterview.enums.TransactionTypes;
import com.practicalinterview.repository.AccountRepository;
import com.practicalinterview.repository.CustomerRepository;
import com.practicalinterview.repository.TransactionRepository;
import com.practicalinterview.response.AccountBalanceResponse;
import com.practicalinterview.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomerAccountUtil {
    @Autowired
    private CustomerRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GetAuthenticatedUserUtil authenticatedUser;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public BaseResponse depositAccount(long userId, double amount) {
        try {
            Customer user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                Transaction transaction = Transaction.builder()
                        .account(accountRepository.findByCustomerId(userId))
                        .amount(amount)
                        .transactionType(TransactionTypes.DEPOSIT)
                        .build();
                transactionRepository.save(transaction);
                Account account = accountRepository.findByCustomerId(userId);
                account.setBalance(account.getBalance()+amount);
                return new BaseResponse(accountRepository.save(account));
            }
            return new BaseResponse("Failed to deposit");
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public BaseResponse withdrawFromCustomerAccount(double amount) {
        Customer user = (Customer) authenticatedUser.getCurrentUser();
        AccountBalanceResponse balanceResponse = accountRepository.accountBalance(user.getId());
        if (balanceResponse == null) balanceResponse = new AccountBalanceResponse(0.0);
        Transaction transaction = Transaction.builder()
                .account(accountRepository.findByCustomerId(user.getId()))
                .amount(amount * -1)
                .transactionType(TransactionTypes.WITHDRAWAL)
                .build();
        transactionRepository.save(transaction);
        Account account = accountRepository.findByCustomerId(user.getId());
        account.setBalance(account.getBalance() - amount);
        account.getTransactions().add(transaction);
        return new BaseResponse(accountRepository.save(account));
    }

    public AccountBalanceResponse getWalletBalance() {
        Customer user = authenticatedUser.getCurrentUser();
        if (user == null){
            return new AccountBalanceResponse(0.0);
        }
        Account account = accountRepository.findByCustomerId(user.getId());
        AccountBalanceResponse response = accountRepository.accountBalance(account.getAccountId());
        if (response == null){
            response = new AccountBalanceResponse(0.0);
        }
        return response;
    }

    public BaseResponse getMiniStatement(long userId) {
        Pageable pageable = PageRequest.of(0, 10);
        return new BaseResponse(accountRepository.getMiniStatement(userId,pageable));
    }

}
