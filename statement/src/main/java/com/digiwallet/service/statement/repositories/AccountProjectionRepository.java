package com.digiwallet.service.statement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digiwallet.service.statement.entities.AccountProjection;

@Repository
public interface AccountProjectionRepository extends JpaRepository<AccountProjection, Long>{
    
    public AccountProjection findByAccountId(Long accountId);

}
