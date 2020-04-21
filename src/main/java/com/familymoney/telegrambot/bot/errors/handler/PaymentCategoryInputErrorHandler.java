package com.familymoney.telegrambot.bot.errors.handler;

import com.familymoney.telegrambot.bot.errors.PaymentCategoryInputException;
import com.familymoney.telegrambot.bot.errors.PaymentTypeInputException;
import com.familymoney.telegrambot.business.model.PaymentCategory;
import com.familymoney.telegrambot.business.service.payment.PaymentCategoryService;
import com.familymoney.telegrambot.business.service.payment.PaymentTypeService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentCategoryInputErrorHandler implements ErrorHandler<PaymentCategoryInputException> {
    private PaymentCategoryService paymentCategoryService;

    public PaymentCategoryInputErrorHandler(PaymentCategoryService paymentCategoryService) {
        this.paymentCategoryService = paymentCategoryService;
    }

    @Override
    public Mono<? extends BotApiMethod<?>> handle(Long chatId, PaymentCategoryInputException exception) {
        return paymentCategoryService.getAll(chatId).collect(Collectors.toList())
            .map(paymentTypes -> {
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = paymentTypes.stream()
                        .map(paymentCategory ->
                                new InlineKeyboardButton().setText(paymentCategory.getName()).setCallbackData(paymentCategory.getName()))
                        .collect(Collectors.toList());

                rowsInline.add(rowInline);
                markupInline.setKeyboard(rowsInline);

                return new SendMessage().setChatId(chatId).setText(exception.getMessage()).setReplyMarkup(markupInline);
            });
    }
}