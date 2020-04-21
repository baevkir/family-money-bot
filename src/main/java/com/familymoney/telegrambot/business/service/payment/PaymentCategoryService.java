package com.familymoney.telegrambot.business.service.payment;

import com.familymoney.telegrambot.business.model.PaymentCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentCategoryService {
    Flux<PaymentCategory> getAll(Long chatId);
    Mono<PaymentCategory> get(Long id);
    Mono<PaymentCategory> create(PaymentCategory paymentType);
    Mono<PaymentCategory> resolve(PaymentCategory paymentType);
}
