package com.example.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics="testing")
    public void consumeMessage(String message){
        System.out.println("message consumed : "+message);
    }


}
