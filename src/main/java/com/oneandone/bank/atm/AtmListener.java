package com.oneandone.bank.atm;

import com.oneandone.bank.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.MapMessage;

//TODO JMS - Make this a bean class
public class AtmListener {

    @Autowired
    AccountService accountService;

    //TODO JMS - make this a jms listener method to receive messages from the "atm-queue"
    public void receiveMessage(MapMessage message) throws JMSException {
        int userId = message.getInt("userId");
        int accountId = message.getInt("accountId");
        double amount = message.getDouble("amount");
        System.out.println("Received < userId: " + userId + " accountId: " + accountId + " amount: " + amount + ">");
        //TODO JMS - use the accountService to get the account and then to redraw the money
    }
}
