package com.digiwallet.service.statement.listeners;

import java.time.Instant;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.digiwallet.service.statement.config.RabbitMqConfig;
import com.digiwallet.service.statement.constants.EventType;
import com.digiwallet.service.statement.entities.AccountProjection;
import com.digiwallet.service.statement.entities.TransactionRecord;
import com.digiwallet.service.statement.pojos.MoneyPojo;
import com.digiwallet.service.statement.pojos.PayloadPojo;
import com.digiwallet.service.statement.pojos.TransactionType;
import com.digiwallet.service.statement.repositories.AccountProjectionRepository;
import com.digiwallet.service.statement.repositories.TransactionRecordRepository;
import com.digiwallet.service.statement.wrappers.EventWrapper;
import com.google.gson.Gson;

import jakarta.transaction.Transactional;

@Component
public class StatementEventListener {
    
    private final AccountProjectionRepository accountProjectionRepository;

    private final TransactionRecordRepository transactionRecordRepository;

    public StatementEventListener(AccountProjectionRepository accountProjectionRepository, 
        TransactionRecordRepository transactionRecordRepository
    ){
        this.accountProjectionRepository = accountProjectionRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    private final Gson gson = new Gson();

    @Transactional
    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void recieveMessage(String message){
        EventWrapper eventWrapper = gson.fromJson(message, EventWrapper.class);
        EventType eventType = eventWrapper.getEventType();
        if (eventType == null) {
            return;
        }

        PayloadPojo payload = gson.fromJson(eventWrapper.getPayload(), PayloadPojo.class);

        Long accountId = payload.getAccountId().getValue();
        Instant now = Instant.now();

        if (EventType.ACCOUNT_OPENED.equals(eventWrapper.getEventType())){
            MoneyPojo money = payload.getInitialBalance();

            accountProjectionRepository.save(new AccountProjection(accountId, money.getAmount(), money.getCurrency(), now));
            transactionRecordRepository.save(new TransactionRecord(accountId, TransactionType.ACCOUNT_OPENED, now, money.getAmount(), money.getCurrency()));
        
        } else if(EventType.MONEY_DEPOSITED.equals(eventWrapper.getEventType())){
            MoneyPojo money = payload.getAmount();

            AccountProjection account = accountProjectionRepository.findByAccountId(accountId);
            account.setBalance(account.getBalance().add(money.getAmount()));
            account.setLastUpdated(now);

            accountProjectionRepository.save(account);

            transactionRecordRepository.save(new TransactionRecord(accountId, TransactionType.MONEY_DEPOSITED, now, money.getAmount(), money.getCurrency()));

        } else if(EventType.MONEY_WITHDRAWN.equals(eventWrapper.getEventType())){
            MoneyPojo money = payload.getAmount();

            AccountProjection account = accountProjectionRepository.findByAccountId(accountId);
            account.setBalance(account.getBalance().subtract(money.getAmount()));
            account.setLastUpdated(now);
            
            accountProjectionRepository.save(account);

            transactionRecordRepository.save(new TransactionRecord(accountId, TransactionType.MONEY_WITHDRAWN, now, money.getAmount(), money.getCurrency()));
        }

    }
    

}
