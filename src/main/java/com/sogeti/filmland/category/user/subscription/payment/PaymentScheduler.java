package com.sogeti.filmland.category.user.subscription.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class PaymentScheduler {

    private final Logger LOGGER = Logger.getLogger(PaymentScheduler.class.toString());

    private final PaymentService paymentService;

    @Autowired
    public PaymentScheduler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Scheduled(cron = "@monthly")
    public void processPayments() {
        LOGGER.info("PAYMENT PROCESS STARTED");

        paymentService.regularMonthlyPayment();
        paymentService.newSubscribersPayment();

        LOGGER.info("PAYMENT PROCESS FINISHED");
    }
}
