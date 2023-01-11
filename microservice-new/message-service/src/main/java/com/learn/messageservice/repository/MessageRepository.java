package com.learn.messageservice.repository;

import com.learn.messageservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MessageRepository extends JpaRepository<Message,Long> {
}
