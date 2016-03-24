package com.oneandone.bank.atm;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

//TODO JMS - Make this a service bean
public class AtmService {

    //TODO JMS - field injection
    JmsTemplate jmsTemplate;

    public void redrawMoney(Integer userId, Integer accountId, Double amount) {
        MessageCreator messageCreator = getMessageCreator(userId, accountId, amount);
        //TODO JMS - use jms template to send a new message to the atm-queue
    }

    private MessageCreator getMessageCreator(final Integer userId, final Integer accountId, final Double amount) {
        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                //TODO JMS - set the payload of the message
                return message;
            }
        };
    }
}
