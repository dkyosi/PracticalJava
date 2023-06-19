package com.practicalinterview.repository;

import com.practicalinterview.datalayer.Account;
import com.practicalinterview.datalayer.Transaction;
import com.practicalinterview.response.AccountBalanceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByCustomerId(long customerId);
    @Query("select new com.practicalinterview.response.AccountBalanceResponse(sum(u.amount) ) from Transaction u where u.account.accountId =:accountId")
    AccountBalanceResponse accountBalance(@Param("accountId") long accountId);
    @Query("select u from Transaction u where u.account.accountId =:accountId ORDER BY u.transactionId DESC")
    List<Transaction> getMiniStatement(@Param("accountId") long accountId, Pageable pageable);
}
