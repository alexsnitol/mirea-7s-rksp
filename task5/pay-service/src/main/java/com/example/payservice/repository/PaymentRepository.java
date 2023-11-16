package com.example.payservice.repository;

import com.example.payservice.model.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PaymentRepository extends ReactiveCrudRepository<Payment, UUID> {
}
