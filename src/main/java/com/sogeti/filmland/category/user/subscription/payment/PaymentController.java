package com.sogeti.filmland.category.user.subscription.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/category/user/payment")
public class PaymentController {

    private final Logger LOGGER = Logger.getLogger(PaymentScheduler.class.toString());

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/start-process")
    public void manuallyProcess() {
        LOGGER.info("PAYMENT PROCESS STARTED");

        paymentService.regularMonthlyPayment();
        paymentService.newSubscribersPayment();

        LOGGER.info("PAYMENT PROCESS FINISHED");
    }
}
