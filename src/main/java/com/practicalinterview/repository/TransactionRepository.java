package com.practicalinterview.repository;

import com.practicalinterview.datalayer.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
