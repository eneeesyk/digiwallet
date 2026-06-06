package com.digital.wallet.project.account.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.wallet.project.account.infrastructure.entities.EventEntity;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByAccountIdOrderByOccurredAtAsc(Long accountId);
    
}
