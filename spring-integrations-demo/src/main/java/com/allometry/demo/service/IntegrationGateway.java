package com.allometry.demo.service;


import com.allometry.demo.model.Address;
import com.allometry.demo.model.Student;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface IntegrationGateway {


    @Gateway(requestChannel = "integration.gateway.channel")
     String sendMessage(String messag);

    @Gateway(requestChannel = "integration.student.gateway.channel")
    String processStudentDetails(Student student);

    @Gateway(requestChannel = "integration.address.gateway.channel")
    String processAddressdetails(Address address);

    @Gateway(requestChannel = "router.channel")
    <T> void process(T object);

}
