package com.allometry.demo.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


@Component
public class AddressService {



    @ServiceActivator(inputChannel = "address.channel")
    public void receiveMessage1(Message<?> message) throws MessagingException {

        System.out.println("***************address.channel************************");
        System.out.println(message);
        System.out.println("***************address.channel************************");
        System.out.println(message.getPayload());

    }
}
