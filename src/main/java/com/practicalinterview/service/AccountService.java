package com.practicalinterview.service;

import com.practicalinterview.datalayer.Account;
import com.practicalinterview.datalayer.Customer;
import com.practicalinterview.repository.AccountRepository;
import com.practicalinterview.repository.CustomerRepository;
import com.practicalinterview.request.CreateAccountRequest;
import com.practicalinterview.request.DepositFundsRequest;
import com.practicalinterview.request.TransferFundsRequest;
import com.practicalinterview.request.WithdrawFundsRequest;
import com.practicalinterview.response.BaseResponse;
import com.practicalinterview.util.CustomerAccountUtil;
import com.practicalinterview.util.GetAuthenticatedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerAccountUtil customerAccountUtil;
    @Lazy
    @Autowired
    private GetAuthenticatedUserUtil authenticatedUser;


    public BaseResponse depositToAccount(DepositFundsRequest request) {
        return new BaseResponse(customerAccountUtil.depositAccount(request.getUserId(), request.getAmount()));
    }
    @Transactional
    public BaseResponse withdrawFromAccount(WithdrawFundsRequest request) {
        try {
            Customer user = customerRepository.findById(request.getUserId()).orElse(null);
            if (user != null) {
                if (customerAccountUtil.getWalletBalance().getBalance() - request.getAmount() < 0){
                    throw new RuntimeException("Insufficient funds");
                }
                return customerAccountUtil.withdrawFromCustomerAccount(request.getAmount());
            }
            return new BaseResponse("Failed to withdraw");
        } catch (Exception e) {
            throw e;
        }
    }

    public BaseResponse getAccountBalance(long userId) {
        try {
            return new BaseResponse(customerAccountUtil.getWalletBalance());
        } catch (Exception e) {
            throw e;
        }
    }


    public BaseResponse getAccount(long userId) {
        try {
            Account account = accountRepository.findByCustomerId(userId);
            if (account != null) {
                return new BaseResponse(account);
            }
            throw new RuntimeException("Account not found!");
        } catch (Exception e) {
            throw e;
        }
    }

    public BaseResponse getAllAccounts() {
        try {
            return new BaseResponse(accountRepository.findAll());
        } catch (Exception e) {
            throw e;
        }
    }

    public BaseResponse createAccount(CreateAccountRequest request) {
        try {
            Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
            if (customer == null) {
                throw new RuntimeException("Customer not found!");
            }
            Account acc = accountRepository.findByCustomerId(request.getCustomerId());
            if (acc != null) {
                throw new RuntimeException("You already have an account!");
            }
            Account account = Account.builder()
                    .balance(0.0)
                    .customer(customer)
                    .build();
            return new BaseResponse(accountRepository.save(account));
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public BaseResponse transferFindsToAnotherAccount(TransferFundsRequest request) {
        try {
            Customer currentCustomer = authenticatedUser.getCurrentUser();
            if (currentCustomer == null) {
                throw new RuntimeException("Please login to proceed!");
            }
            Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
            if (customer == null) {
                throw new RuntimeException("Invalid customer!");
            }
            if (customer.getCustomerId().equals(currentCustomer.getCustomerId())) {
                throw new RuntimeException("You cannot transfer to your own account!");
            }
            Account currentCustomerAccount = accountRepository.findByCustomerId(currentCustomer.getId());
            if (currentCustomerAccount.getBalance() - request.getAmount() < 0) {
                throw new RuntimeException("Insufficient balance to transfer funds!");
            }
            customerAccountUtil.withdrawFromCustomerAccount(request.getAmount());
            customerAccountUtil.depositAccount(request.getCustomerId(), request.getAmount());
            return new BaseResponse(customerAccountUtil.getWalletBalance());
        } catch (Exception e) {
            throw e;
        }
    }
}
