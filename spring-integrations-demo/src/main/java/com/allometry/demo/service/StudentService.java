package com.allometry.demo.service;

import com.allometry.demo.model.Student;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class StudentService {

    @ServiceActivator(inputChannel = "integration.student.gateway.objectToJson.channel" , outputChannel = "integration.student.jsonToObject.channel")
    public Message<?> receiveMessage(Message<?> message) throws MessagingException {

        System.out.println("******************************************");
        System.out.println(message);
        System.out.println("******************************************");
        System.out.println("Object to json - "+message.getPayload());
        return  message;

    }


    @ServiceActivator(inputChannel = "integration.student.jsonToObject.fromTransformer.channel")
    public void processJsonToObject(Message<?> message) throws MessagingException {

        MessageChannel replyChannel = (MessageChannel) message.getHeaders().getReplyChannel();
        MessageBuilder.fromMessage(message);
        System.out.println("***************************************");
        System.out.println("Json to object - "+message.getPayload());
        Student student = (Student) message.getPayload();
        Message<?> newMesage = MessageBuilder.withPayload(student.toString()).build();
        replyChannel.send(newMesage);
    }


    @ServiceActivator(inputChannel = "student.channel")
    public void receiveMessage1(Message<?> message) throws MessagingException {

        System.out.println("***************student.channel************************");
        System.out.println(message);
        System.out.println("***************student.channel************************");
        System.out.println(message.getPayload());

    }

    @ServiceActivator(inputChannel = "student.channel.1")
    public void receiveMessage11(Message<?> message) throws MessagingException {

        System.out.println("***************student.channel.1************************");
        System.out.println(message);
        System.out.println("***************student.channel************************");
        System.out.println(message.getPayload());

    }

    @ServiceActivator(inputChannel = "student.channel.2")
    public void receiveMessage12(Message<?> message) throws MessagingException {

        System.out.println("***************student.channel.2************************");
        System.out.println(message);
        System.out.println("***************student.channel************************");
        System.out.println(message.getPayload());

    }
}
