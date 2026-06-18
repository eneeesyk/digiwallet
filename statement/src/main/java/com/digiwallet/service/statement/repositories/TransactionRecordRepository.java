package com.digiwallet.service.statement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digiwallet.service.statement.entities.TransactionRecord;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long>{
    
    public List<TransactionRecord> findByAccountIdOrderByOccurredAtAsc(Long accountId);
}
