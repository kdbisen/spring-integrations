package com.allometry.demo.config;

import com.allometry.demo.model.Address;
import com.allometry.demo.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSelector;
import org.springframework.integration.filter.MessageFilter;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {

    @Bean
    public MessageChannel receiverChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }


    @Bean
    @Filter(inputChannel = "router.channel")
    public MessageFilter messageFilter(){
        MessageFilter messageFilter = new MessageFilter(new MessageSelector() {
            @Override
            public boolean accept(Message<?> message) {

                System.out.println("inside accept method &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                return message.getPayload() instanceof  Student;
            }
        });
        messageFilter.setOutputChannelName("student.channel");
        return  messageFilter;
    }

  /*@ServiceActivator(inputChannel = "router.channel")
    @Bean
    public PayloadTypeRouter router1() throws MessagingException {

        PayloadTypeRouter payloadTypeRouter = new PayloadTypeRouter();
        payloadTypeRouter.setChannelMapping(Student.class.getName(), "student.enrich.header.channel");
        payloadTypeRouter.setChannelMapping(Address.class.getName(), "address.enrich.header.channel");
        return  payloadTypeRouter;
    }
*/

    @ServiceActivator(inputChannel = "header.payload.router.channel")
    @Bean
    public HeaderValueRouter headerValueRouter() throws MessagingException {

        HeaderValueRouter headerValueRouter = new HeaderValueRouter("testHeader");
        headerValueRouter.setChannelMapping("student", "student.channel");
        headerValueRouter.setChannelMapping("address", "address.channel");
        return  headerValueRouter;
    }

/*
    @ServiceActivator(inputChannel = "router.channel")
    @Bean
    public RecipientListRouter router2() throws MessagingException {

        RecipientListRouter recipientListRouter = new RecipientListRouter();
        recipientListRouter.addRecipient("student.channel.1");
        recipientListRouter.addRecipient( "student.channel.2");
        return  recipientListRouter;
    }*/


    @Bean
    @Transformer(inputChannel = "student.enrich.header.channel",
            outputChannel = "header.payload.router.channel")
    public HeaderEnricher headerEnricherForStudent(){
        Map<String, HeaderValueMessageProcessor<String>> headersToAdd = new HashMap<>();
        headersToAdd.put("testHeader", new StaticHeaderValueMessageProcessor<>("student"));
        HeaderEnricher headerEnricher = new HeaderEnricher(headersToAdd);
        return headerEnricher;
    }


    @Bean
    @Transformer(inputChannel = "address.enrich.header.channel",
            outputChannel = "header.payload.router.channel")
    public HeaderEnricher headerEnricherForAddress(){
        Map<String, HeaderValueMessageProcessor<String>> headersToAdd = new HashMap<>();
        headersToAdd.put("testHeader", new StaticHeaderValueMessageProcessor<>("address"));
        HeaderEnricher headerEnricher = new HeaderEnricher(headersToAdd);
        return headerEnricher;
    }






    @Bean
    @Transformer(inputChannel = "integration.student.toConvertObject.channel",
            outputChannel = "integration.student.gateway.objectToJson.channel")
    public ObjectToJsonTransformer objectToJsonTransformer(){
        return new ObjectToJsonTransformer(getMapper());
    }

    @Bean
    public Jackson2JsonObjectMapper getMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonObjectMapper(objectMapper);
    }

    @Bean
    @Transformer(inputChannel = "integration.student.jsonToObject.channel", outputChannel = "integration.student.jsonToObject.fromTransformer.channel")
    public JsonToObjectTransformer jsonToObjectTransformer(){
        return new JsonToObjectTransformer(Student.class);
    }

    @Bean
    @Transformer(inputChannel = "integration.student.gateway.channel",
            outputChannel = "integration.student.toConvertObject.channel")
    public HeaderEnricher headerEnricher(){
        Map<String, HeaderValueMessageProcessor<String>> headersToAdd = new HashMap<>();

        headersToAdd.put("header1", new StaticHeaderValueMessageProcessor<>("Test Header 1"));
        headersToAdd.put("header2", new StaticHeaderValueMessageProcessor<>("Test Header 2"));
        HeaderEnricher headerEnricher = new HeaderEnricher(headersToAdd);
        return headerEnricher;
    }


}
