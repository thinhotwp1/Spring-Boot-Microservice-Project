package com.learn.messageservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String oderNumber;
}
