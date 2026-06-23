package com.digital.wallet.project.account.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import com.digital.wallet.project.account.application.services.AccountService;
import com.digital.wallet.project.account.domain.Account;
import com.digital.wallet.project.account.domain.Money;
import com.digital.wallet.project.account.infrastructure.messaging.wrappers.EventWrapper;
import com.digital.wallet.project.account.objects.AccountId;
import com.digital.wallet.project.config.RabbitMqFanoutExchangeConfig;
import com.google.gson.Gson;

import com.digital.wallet.project.constants.EventType;
import com.digital.wallet.project.pojos.TransferCompensateCompleted;
import com.digital.wallet.project.pojos.TransferCompensateFailed;
import com.digital.wallet.project.pojos.TransferCompensateRequestedPayload;
import com.digital.wallet.project.pojos.TransferDepositCompletedPayload;
import com.digital.wallet.project.pojos.TransferDepositFailedPayload;
import com.digital.wallet.project.pojos.TransferDepositRequestedPayload;
import com.digital.wallet.project.pojos.TransferWithdrawCompletedPayload;
import com.digital.wallet.project.pojos.TransferWithdrawFailedPayload;
import com.digital.wallet.project.pojos.TransferWithdrawRequestedPayload;

@Component
public class TransferEventListener {

    private final AccountService accountService;
    private final EventPublisher eventPublisher;

    private Gson gson = new Gson();

    public TransferEventListener(AccountService accountService, EventPublisher eventPublisher){
        this.accountService = accountService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @RabbitListener(queues = RabbitMqFanoutExchangeConfig.TRANSFER_QUEUE_NAME)
    public void recieveMessage(String message){
        EventWrapper eventWrapper = gson.fromJson(message, EventWrapper.class);

        if (EventType.TRANSFER_WITHDRAW_REQUESTED.equals(eventWrapper.getEventType())) {
            TransferWithdrawRequestedPayload transferWithdrawRequestedPayload = gson.fromJson(eventWrapper.getPayload(), TransferWithdrawRequestedPayload.class);
            AccountId fromAccountId = new AccountId(transferWithdrawRequestedPayload.getFromAccountId());

            Account account = accountService.loadAccount(fromAccountId);

            try {
                account.withdraw(new Money(transferWithdrawRequestedPayload.getAmount(), transferWithdrawRequestedPayload.getCurrency()));
                accountService.registerEvents(account);

                TransferWithdrawCompletedPayload transferWithdrawCompletedPayload =
                 new TransferWithdrawCompletedPayload(transferWithdrawRequestedPayload.getTransferId(),
                    transferWithdrawRequestedPayload.getFromAccountId(), 
                    transferWithdrawRequestedPayload.getAmount(),
                    transferWithdrawRequestedPayload.getCurrency(),
                    transferWithdrawRequestedPayload.getToAccountId());
                eventPublisher.publishTransferEvent(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, "", transferWithdrawCompletedPayload, EventType.TRANSFER_WITHDRAW_COMPLETED);
            } catch (Exception e) {
                TransferWithdrawFailedPayload transferWithdrawFailedPayload =
                 new TransferWithdrawFailedPayload(transferWithdrawRequestedPayload.getTransferId(),
                 e.getMessage());

                eventPublisher.publishTransferEvent(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, "", transferWithdrawFailedPayload, EventType.TRANSFER_WITHDRAW_FAILED);
            }

                        
        } else if(EventType.TRANSFER_DEPOSIT_REQUESTED.equals(eventWrapper.getEventType())){
            TransferDepositRequestedPayload transferDepositRequestedPayload = gson.fromJson(eventWrapper.getPayload(), TransferDepositRequestedPayload.class);
            AccountId toAccountId = new AccountId(transferDepositRequestedPayload.getToAccountId());
        
            Account account = accountService.loadAccount(toAccountId);

            try {
                account.deposit(new Money(transferDepositRequestedPayload.getAmount(), transferDepositRequestedPayload.getCurrency()));
                accountService.registerEvents(account);

                TransferDepositCompletedPayload transferDepositCompletedPayload =
                    new TransferDepositCompletedPayload(transferDepositRequestedPayload.getTransferId(),
                        transferDepositRequestedPayload.getFromAccountId(),
                        transferDepositRequestedPayload.getAmount(),
                        transferDepositRequestedPayload.getCurrency(),
                        transferDepositRequestedPayload.getToAccountId());
                eventPublisher.publishTransferEvent(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, "", transferDepositCompletedPayload, EventType.TRANSFER_DEPOSIT_COMPLETED);
            }catch (Exception e){
                TransferDepositFailedPayload transferDepositFailedPayload = 
                    new TransferDepositFailedPayload(transferDepositRequestedPayload.getTransferId(), 
                    transferDepositRequestedPayload.getFromAccountId(), 
                    transferDepositRequestedPayload.getAmount(),
                    transferDepositRequestedPayload.getCurrency(),
                    e.getMessage());

              eventPublisher.publishTransferEvent(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, "", transferDepositFailedPayload, EventType.TRANSFER_DEPOSIT_FAILED);
            }
        } else if (EventType.TRANSFER_COMPENSATE_REQUESTED.equals(eventWrapper.getEventType())) {
            TransferCompensateRequestedPayload transferCompensateRequestedPayload = gson.fromJson(eventWrapper.getPayload(), TransferCompensateRequestedPayload.class);

            AccountId accountId = new AccountId(transferCompensateRequestedPayload.getAccountId());

            Account account = accountService.loadAccount(accountId);

            try {
                account.deposit(new Money(transferCompensateRequestedPayload.getAmount(), transferCompensateRequestedPayload.getCurrency()));
                accountService.registerEvents(account);

                TransferCompensateCompleted transferCompensateCompleted = 
                    new TransferCompensateCompleted(transferCompensateRequestedPayload.getTransferId());

                 eventPublisher.publishTransferEvent(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, "", transferCompensateCompleted, EventType.TRANSFER_COMPENSATED);
            } catch (Exception e){
                TransferCompensateFailed transferCompensateFailed = new TransferCompensateFailed(transferCompensateRequestedPayload.getTransferId());
                eventPublisher.publishTransferEvent(RabbitMqFanoutExchangeConfig.FANOUT_EXCHANGE, "", transferCompensateFailed, EventType.TRANSFER_COMPENSATE_FAILED);
            }
        }
    }
    
}
