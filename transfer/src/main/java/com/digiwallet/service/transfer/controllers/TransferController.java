package com.digiwallet.service.transfer.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digiwallet.service.transfer.StatusEnum;
import com.digiwallet.service.transfer.services.TransferService;
import com.digiwallet.service.transfer.pojos.TransferRequest;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> initiateTransfer(@RequestBody TransferRequest transferRequest){
        transferService.initiateTransfer(transferRequest.getFromAccountId(),
         transferRequest.getToAccountId(), transferRequest.getAmount(), transferRequest.getCurrency());
        return ResponseEntity.ok().build();
    } 

    @GetMapping("/{transferId}/status")
    public ResponseEntity<StatusEnum> getStatus(@PathVariable("transferId") UUID transferId){
        return ResponseEntity.ok(transferService.getTransferStatus(transferId));
    }
    
}
