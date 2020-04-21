package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.model.PaymentType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentTypeService {
    Flux<PaymentType> getAll(Long chatId);
    Mono<PaymentType> create(PaymentType paymentType);
    Mono<PaymentType> resolvePaymentType(PaymentType paymentType);
}