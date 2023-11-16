package com.example.payservice.config;

import com.example.payservice.contracts.generated.Shop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class Web3jConfig {

    @Value("${blockchain.node.url}")
    private String nodeUrl;
    @Value("${blockchain.node.token}")
    private String nodeToken;
    @Value("${blockchain.contract.address}")
    private String contractAddress;
    @Value("${blockchain.contract.owner-private-key}")
    private String ownerPrivateKey;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(nodeUrl + "/" + nodeToken));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(ownerPrivateKey);
    }

    @Bean
    public Shop shop(Web3j web3j, Credentials credentials) {
        Shop shop = Shop.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        return shop;
//        try {
//            if (shop.isValid()) {
//                return shop;
//            } else {
//                throw new RuntimeException("Smart contract is not valid");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Error in time creating smart contract");
//        }
    }

}
