package com.learn.oderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String oderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OderLineItems> oderLineItems ;


}
