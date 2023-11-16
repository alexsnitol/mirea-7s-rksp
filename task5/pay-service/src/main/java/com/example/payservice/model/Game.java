package com.example.payservice.model;

import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class Game {

    private UUID id;
    private String title;
    private BigInteger priceWei;

}
