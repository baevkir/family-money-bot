package com.familymoney.telegrambot.business.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCategory {
    private Long id;
    private Long chatId;
    private String name;
}