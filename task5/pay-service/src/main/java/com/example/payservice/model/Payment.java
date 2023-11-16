package com.example.payservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("payment")
@Data
public class Payment {

    @Id
    private UUID id;
    private String address;
    private Double valueWei;
    private String status;

}
