package com.example.payservice.service.impl;

import com.example.payservice.contracts.generated.Shop;
import com.example.payservice.model.Game;
import com.example.payservice.model.Payment;
import com.example.payservice.repository.PaymentRepository;
import com.example.payservice.service.PaymentService;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final Shop shopAuthOwner;
    private final Web3j web3j;


    public PaymentServiceImpl(PaymentRepository paymentRepository, Shop shop, Web3j web3j) {
        this.paymentRepository = paymentRepository;
        this.shopAuthOwner = shop;
        this.web3j = web3j;
    }

    @Override
    public Mono<Payment> payGameById(UUID gameId, String privateKeyCustomerAccount) {
        return Mono.defer(() -> {
            Shop shopAuthCustomer = Shop.load(
                    shopAuthOwner.getContractAddress(),
                    web3j,
                    Credentials.create(privateKeyCustomerAccount),
                    new DefaultGasProvider()
            );
            BigInteger price;
            try {
                price = shopAuthOwner.getGamePriceByUuid(gameId.toString()).send();
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("Error of getting game price by UUID %s, because: %s", gameId, e.getMessage())
                );
            }
            try {
                TransactionReceipt transaction = shopAuthCustomer.payGame(gameId.toString(), price).send();
                Payment payment = new Payment();
                payment.setAddress(transaction.getTransactionHash());
                payment.setValueWei(price.doubleValue());
                payment.setStatus(transaction.getStatus());
                return paymentRepository.save(payment);
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("Error of paying game by UUID %s, because: %s", gameId, e.getMessage())
                );
            }
        });
    }

    @Override
    public Mono<BigInteger> getBalance() {
        return Mono.defer(() -> {
            try {
                BigInteger balance = web3j.ethGetBalance(shopAuthOwner.getContractAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
                return Mono.just(balance);
            } catch (Exception e) {
                throw new RuntimeException("Error in time getting balance of contract");
            }
        });

    }

    @Override
    public Mono<Void> saveOrUpdateGame(Game game) {
        return Mono.defer(() -> {
            try {
                shopAuthOwner.saveOrUpdateGame(game.getId().toString(), game.getTitle(), game.getPriceWei()).send();
                return Mono.empty();
            } catch (Exception e) {
                throw new RuntimeException(
                        String.format("Error in time adding game with id %s, because: %s", game.getId(), e.getMessage())
                );
            }
        });
    }

}
