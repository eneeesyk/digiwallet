package com.digiwallet.service.transfer.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.digiwallet.service.transfer.StatusEnum;
import com.digiwallet.service.transfer.config.RabbitMqConfig;
import com.digiwallet.service.transfer.constants.EventType;
import com.digiwallet.service.transfer.entities.SagaEntity;
import com.digiwallet.service.transfer.pojos.TransferCompensateCompleted;
import com.digiwallet.service.transfer.pojos.TransferCompensateFailed;
import com.digiwallet.service.transfer.pojos.TransferCompensateRequestedPayload;
import com.digiwallet.service.transfer.pojos.TransferDepositCompletedPayload;
import com.digiwallet.service.transfer.pojos.TransferDepositFailedPayload;
import com.digiwallet.service.transfer.pojos.TransferDepositRequestedPayload;
import com.digiwallet.service.transfer.pojos.TransferWithdrawCompletedPayload;
import com.digiwallet.service.transfer.pojos.TransferWithdrawFailedPayload;
import com.digiwallet.service.transfer.publishers.TransferPublisher;
import com.digiwallet.service.transfer.repositories.SagaRepository;
import com.digiwallet.service.transfer.wrappers.EventWrapper;
import com.google.gson.Gson;

import jakarta.transaction.Transactional;

@Component
public class TransferEventListener {
    
    private Gson gson = new Gson();

    private final SagaRepository sagaRepository;
    private final TransferPublisher transferPublisher;

    public TransferEventListener(SagaRepository sagaRepository, TransferPublisher transferPublisher) {
        this.sagaRepository = sagaRepository;
        this.transferPublisher = transferPublisher;
    }

    @Transactional
    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void recieveMessage(String message){
        EventWrapper eventWrapper = gson.fromJson(message, EventWrapper.class);

        if (EventType.TRANSFER_WITHDRAW_COMPLETED.equals(eventWrapper.getEventType())) {
            TransferWithdrawCompletedPayload transferWithdrawCompletedPayload = 
                gson.fromJson(eventWrapper.getPayload(), TransferWithdrawCompletedPayload.class);
            SagaEntity sagaEntity = sagaRepository.findByTransferId(transferWithdrawCompletedPayload.getTransferId());

            sagaEntity.setStatus(StatusEnum.WITHDRAWN);
            sagaRepository.save(sagaEntity);

            TransferDepositRequestedPayload transferDepositRequestedPayload =
                new TransferDepositRequestedPayload(transferWithdrawCompletedPayload.getTransferId(),
                transferWithdrawCompletedPayload.getFromAccountId(),
                transferWithdrawCompletedPayload.getToAccountId(),
                transferWithdrawCompletedPayload.getAmount(),
                transferWithdrawCompletedPayload.getCurrency());

            transferPublisher.publishMessage(RabbitMqConfig.EXCHANGE_NAME, "", transferDepositRequestedPayload, EventType.TRANSFER_DEPOSIT_REQUESTED);
        } else if (EventType.TRANSFER_WITHDRAW_FAILED.equals(eventWrapper.getEventType())) {
            TransferWithdrawFailedPayload transferWithdrawFailedPayload = 
                gson.fromJson(eventWrapper.getPayload(), TransferWithdrawFailedPayload.class);
            SagaEntity sagaEntity = sagaRepository.findByTransferId(transferWithdrawFailedPayload.getTransferId());

            sagaEntity.setStatus(StatusEnum.FAILED);
            sagaRepository.save(sagaEntity);
        } else if(EventType.TRANSFER_DEPOSIT_COMPLETED.equals(eventWrapper.getEventType())){
            TransferDepositCompletedPayload transferDepositCompletedPayload =
                gson.fromJson(eventWrapper.getPayload(), TransferDepositCompletedPayload.class);

            SagaEntity sagaEntity = sagaRepository.findByTransferId(transferDepositCompletedPayload.getTransferId());
            sagaEntity.setStatus(StatusEnum.COMPLETED);

            sagaRepository.save(sagaEntity);
        } else if (EventType.TRANSFER_DEPOSIT_FAILED.equals(eventWrapper.getEventType())){
            TransferDepositFailedPayload transferDepositFailedPayload = gson.fromJson(eventWrapper.getPayload(), TransferDepositFailedPayload.class);

            SagaEntity sagaEntity = sagaRepository.findByTransferId(transferDepositFailedPayload.getTransferId());

            sagaEntity.setStatus(StatusEnum.COMPENSATING);
            sagaRepository.save(sagaEntity);

            TransferCompensateRequestedPayload transferCompensateRequestedPayload = 
                new TransferCompensateRequestedPayload(transferDepositFailedPayload.getTransferId(),
                transferDepositFailedPayload.getFromAccountId(),
                transferDepositFailedPayload.getAmount(),
                transferDepositFailedPayload.getCurrency());

            transferPublisher.publishMessage(RabbitMqConfig.EXCHANGE_NAME, "", transferCompensateRequestedPayload, EventType.TRANSFER_COMPENSATE_REQUESTED);
       } else if (EventType.TRANSFER_COMPENSATED.equals(eventWrapper.getEventType())) {
            TransferCompensateCompleted transferCompensateCompleted = gson.fromJson(eventWrapper.getPayload(), TransferCompensateCompleted.class);

            SagaEntity sagaEntity = sagaRepository.findByTransferId(transferCompensateCompleted.getTransferId());

            sagaEntity.setStatus(StatusEnum.COMPENSATED);
            sagaRepository.save(sagaEntity);
       
       } else if (EventType.TRANSFER_COMPENSATE_FAILED.equals(eventWrapper.getEventType())) {
            TransferCompensateFailed transferCompensateFailed = gson.fromJson(eventWrapper.getPayload(), TransferCompensateFailed.class);

            SagaEntity sagaEntity = sagaRepository.findByTransferId(transferCompensateFailed.getTransferId());

            sagaEntity.setStatus(StatusEnum.FAILED);
            sagaRepository.save(sagaEntity);
       }
    }
}
