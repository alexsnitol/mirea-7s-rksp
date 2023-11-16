package com.example.payservice.controller;

import com.example.payservice.model.Game;
import com.example.payservice.model.Payment;
import com.example.payservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@RequestMapping("/pay-service/api/operations")
@RestController
public class PaymentController {

    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Operation(
            description = "Paying game by id",
            parameters = {
                    @Parameter(
                            name = "privateKeyConsumerAccount",
                            description = "Private key of consumer account/wallet via which will be pay game"
                    )
            }
    )
    @PostMapping("/pay-game")
    public Mono<Payment> payGame(@RequestParam UUID gameId, @RequestParam String privateKeyConsumerAccount) {
        return paymentService.payGameById(gameId, privateKeyConsumerAccount);
    }

    @Operation(description = "Getting balance of game market contract")
    @GetMapping("/balance")
    public Mono<BigInteger> getBalance() {
        return paymentService.getBalance();
    }

    @Operation(description = "Save or update game in game shop contract")
    @PutMapping("/game")
    public Mono<Void> saveOrUpdateGame(@RequestBody Game game) {
        return paymentService.saveOrUpdateGame(game);
    }

}
