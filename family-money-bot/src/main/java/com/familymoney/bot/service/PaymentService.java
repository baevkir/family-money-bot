package com.familymoney.bot.service;

import com.familymoney.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentService {
    Flux<Payment> getAllByTelegramUserId(Integer telegramId);
    Mono<Payment> create(Payment payment);
}
