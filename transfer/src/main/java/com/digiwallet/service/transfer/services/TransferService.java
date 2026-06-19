package com.digiwallet.service.transfer.services;

import java.math.BigDecimal;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.digiwallet.service.transfer.StatusEnum;
import com.digiwallet.service.transfer.config.RabbitMqConfig;
import com.digiwallet.service.transfer.constants.EventType;
import com.digiwallet.service.transfer.entities.SagaEntity;
import com.digiwallet.service.transfer.pojos.TransferWithdrawRequestedPayload;
import com.digiwallet.service.transfer.publishers.TransferPublisher;
import com.digiwallet.service.transfer.repositories.SagaRepository;
import com.digiwallet.service.transfer.wrappers.EventWrapper;

import com.google.gson.Gson;


@Service
public class TransferService {

    private final SagaRepository sagaRepository;
    private final TransferPublisher transferPublisher;

    private Gson gson = new Gson();

    public TransferService(SagaRepository sagaRepository, TransferPublisher transferPublisher){
        this.sagaRepository = sagaRepository;
        this.transferPublisher = transferPublisher;
    }
    

    public void initiateTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String currency){
        SagaEntity entity = new SagaEntity();
        TransferWithdrawRequestedPayload transferWithdrawRequestedPayload = new TransferWithdrawRequestedPayload();

        UUID transferId = UUID.randomUUID();

        entity.setTransferId(transferId);
        entity.setAmount(amount);
        entity.setCurrency(currency);
        entity.setFromAccountId(fromAccountId);
        entity.setStatus(StatusEnum.INITIATED);
        entity.setToAccountId(toAccountId);
        sagaRepository.save(entity);

        transferWithdrawRequestedPayload.setAmount(amount);
        transferWithdrawRequestedPayload.setCurrency(currency);
        transferWithdrawRequestedPayload.setFromAccountId(fromAccountId);
        transferWithdrawRequestedPayload.setTransferId(transferId);

        EventWrapper eventWrapper = new EventWrapper(EventType.TRANSFER_WITHDRAW_REQUESTED, gson.toJson(transferWithdrawRequestedPayload));
        transferPublisher.publishMessage(RabbitMqConfig.EXCHANGE_NAME, "", eventWrapper);

        entity.setStatus(StatusEnum.WITHDRAW_REQUESTED);
        sagaRepository.save(entity);


    }

    public StatusEnum getTransferStatus(UUID transferId){
        return sagaRepository.findByTransferId(transferId).getStatus();
    }
}
