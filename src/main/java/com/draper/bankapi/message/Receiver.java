package com.draper.bankapi.message;

import com.draper.bankapi.business.ValidateDeleteAccountsRequest;
import com.draper.bankapi.business.service.AccountService;
import com.draper.bankapi.common.Constants;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final AccountService accountService;

    public Receiver(AccountService accountService) {
        this.accountService = accountService;
    }

    @JmsListener(destination = Constants.JMS_LISTENER_DESTINATION)
    public void receiveMessage(DeleteAccountsRequest request) {
        ValidateDeleteAccountsRequest.perform(request);

        long numberOfAccountsDeleted = accountService.deleteAccounts(request.getAccountIds());
        System.out.format("Deleted %d accounts", numberOfAccountsDeleted);
    }
}
