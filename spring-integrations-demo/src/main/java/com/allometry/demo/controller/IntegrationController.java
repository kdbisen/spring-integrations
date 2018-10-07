package com.allometry.demo.controller;


import com.allometry.demo.model.Address;
import com.allometry.demo.model.Student;
import com.allometry.demo.service.IntegrationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integrate")
public class IntegrationController {

    @Autowired
    private IntegrationGateway integrationGateway;

    @GetMapping(value = "{name}")
    public String getMessageFromIntegrationService(@PathVariable("name") String name)
    {
       return integrationGateway.sendMessage(name);

    }

    @PostMapping
    public String processStudentdetails(@RequestBody Student student){

        return integrationGateway.processStudentDetails(student);
    }

    @PostMapping("/student")
    public void processStudent(@RequestBody Student student){

          integrationGateway.process(student);
    }

    @PostMapping("/address")
    public void processAddress(@RequestBody Address address){

          integrationGateway.process(address);
    }

}
