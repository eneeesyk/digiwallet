package com.digital.wallet.project.account.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digital.wallet.project.account.infrastructure.entities.EventEntity;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByAccountIdOrderByOccurredAtAsc(Long accountId);
    
}
