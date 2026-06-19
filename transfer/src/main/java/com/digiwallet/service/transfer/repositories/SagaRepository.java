package com.digiwallet.service.transfer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digiwallet.service.transfer.entities.SagaEntity;
import java.util.UUID;


public interface SagaRepository extends JpaRepository<SagaEntity, UUID>{

    SagaEntity findByTransferId(UUID transferId);
    
}
