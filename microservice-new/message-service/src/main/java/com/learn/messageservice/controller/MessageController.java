package com.learn.messageservice.controller;

import com.learn.messageservice.model.Message;
import com.learn.messageservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageController {
    private final MessageService messageService;


    @RabbitListener(queues = "queue.A")
    private void receiveFromA(Message message){
        log.info("Message recevied from QUEUEA->{}",message);
        messageService.save(message);
        log.info("Success !");
    }
}
