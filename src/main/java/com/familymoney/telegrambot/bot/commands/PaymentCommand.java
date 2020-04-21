package com.familymoney.telegrambot.bot.commands;

import com.familymoney.telegrambot.bot.commands.annotations.CommandMethod;
import com.familymoney.telegrambot.bot.commands.annotations.Param;
import com.familymoney.telegrambot.bot.errors.PaymentCategoryInputException;
import com.familymoney.telegrambot.bot.errors.PaymentTypeInputException;
import com.familymoney.telegrambot.business.mapper.UserMapper;
import com.familymoney.telegrambot.business.model.Account;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.service.payment.PaymentService;
import com.familymoney.telegrambot.business.service.UserService;
import com.familymoney.telegrambot.business.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class PaymentCommand extends ReactiveBotCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpCommand.class);

    private PaymentService paymentService;
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public PaymentCommand(PaymentService paymentService, UserService userService, UserMapper userMapper) {
        super("payment", "Создать платеж в системе.");
        this.paymentService = paymentService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @CommandMethod
    public Mono<? extends BotApiMethod<?>> process(
            Message command,
            @Param(index = 0, displayName = "Вид оплаты", errorType = PaymentTypeInputException.class) String type,
            @Param(index = 1, displayName = "Категорию", errorType = PaymentCategoryInputException.class) String category,
            @Param(index = 2, displayName = "Сумма") BigDecimal amount) {

        Payment paymentDto = new Payment();
        paymentDto.setChatId(command.getChatId());
        paymentDto.setUser(userMapper.fromTelegramPojo(command.getFrom()));
        paymentDto.setType(Account.builder()
                .chatId(command.getChatId())
                .name(type)
                .build());
        paymentDto.setCategory(PaymentCategory.builder()
            .chatId(command.getChatId())
            .name(category)
            .build());
        paymentDto.setAmount(amount);

        return paymentService.create(paymentDto)
                .map(result -> new SendMessage(
                        command.getChatId(),
                        String.format("Платеж пользователя %s успешно сохранен.", paymentDto.getUser().getUserName()))
                );

    }


}
