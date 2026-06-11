package com.digital.wallet.project.account.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.wallet.project.account.infrastructure.entities.AccountBalanceEntity;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalanceEntity, Long> {
    
    public AccountBalanceEntity findByAccountId(Long accountId);

}
