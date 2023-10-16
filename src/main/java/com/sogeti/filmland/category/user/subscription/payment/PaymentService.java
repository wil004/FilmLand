package com.sogeti.filmland.category.user.subscription.payment;

import com.sogeti.filmland.category.user.UserCategory;
import com.sogeti.filmland.category.user.UserCategoryRepository;
import com.sogeti.filmland.category.user.subscription.shared.SharedSubscription;
import com.sogeti.filmland.category.user.subscription.shared.SharedSubscriptionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final UserCategoryRepository userCategoryRepository;

    private final Map<Integer, SharedSubscription> sharedSubscriptionMap;

    public PaymentService(UserCategoryRepository userCategoryRepository,
                          SharedSubscriptionRepository sharedSubscriptionRepository) {
        this.userCategoryRepository = userCategoryRepository;
        sharedSubscriptionMap = sharedSubscriptionRepository.findAll().stream()
                .collect(Collectors.toMap(s -> s.getSubscriptionA().getId(), s -> s));
    }

    public void regularMonthlyPayment() {
        List<UserCategory> subscriptionList = userCategoryRepository.getSubscriptionsOlderThan2Months();
        List<UserCategory> paidSharedSubscriptions = payAndGetSharedSubscriptions(subscriptionList);
        subscriptionList.removeAll(paidSharedSubscriptions);

        subscriptionList.forEach(s -> aPaymentAPICall(s.getUser().getId(), s.getCategory().getPrice()));
    }

    /* It is more convenient if 3 different subscription payments can be processed all at the same moment in the month
    that's why after your free trial,
    your subscription price will be calculated based on the amount of days (not months).
    */

    public void newSubscribersPayment() {
        List<UserCategory> subscriptionList = userCategoryRepository.getSubscriptionsOlderThan1Month();
        List<UserCategory> paidSharedSubscriptions = payAndGetSharedSubscriptions(subscriptionList);
        subscriptionList.removeAll(paidSharedSubscriptions);

        for(UserCategory newSubscriber : subscriptionList) {
            BigDecimal priceToPay = calculatePriceBasedOnDays(newSubscriber.getCategory().getPrice(),
                    newSubscriber.getStartDate());

            aPaymentAPICall(newSubscriber.getUser().getId(), priceToPay);
        }
    }

    private List<UserCategory> payAndGetSharedSubscriptions(List<UserCategory> subscriptionList) {
        List<UserCategory> processedSharedSubscriptions = new ArrayList<>();

        for(UserCategory subscriber : subscriptionList) {
            SharedSubscription sharedSubscription = sharedSubscriptionMap.get(subscriber.getId());

            BigDecimal priceToPay = subscriber.getCategory().getPrice();

            if(sharedSubscription != null) {
                priceToPay = priceToPay.divide(BigDecimal.valueOf(2), RoundingMode.UNNECESSARY);
                aPaymentAPICall(sharedSubscription.getSubscriptionA().getId(), priceToPay);
                aPaymentAPICall(sharedSubscription.getSubscriptionB().getId(), priceToPay);
                processedSharedSubscriptions.add(sharedSubscription.getSubscriptionA());
                processedSharedSubscriptions.add(sharedSubscription.getSubscriptionB());
            }
        }

        return processedSharedSubscriptions;
    }

    private BigDecimal calculatePriceBasedOnDays(BigDecimal price, LocalDate startDate) {
        BigDecimal averageMonth = BigDecimal.valueOf(365 / 12);

        long daysSubscribed = ChronoUnit.DAYS.between(startDate, LocalDate.now());

        BigDecimal pricePerDay = price.divide(averageMonth, RoundingMode.HALF_DOWN);

        return pricePerDay.multiply(BigDecimal.valueOf(daysSubscribed))
                .setScale(2, RoundingMode.UNNECESSARY);
    }

    private boolean aPaymentAPICall(Integer userId, BigDecimal subscriptionCost) {
        /* To stay within the scope of the Sogeti FilmLand application, the fictional payment service
        will sum up subscription costs per user, and process the payments.
        * */
        try {
            refillRemainingContent();
            return true;
        } catch(RuntimeException e) {
            throw new RuntimeException(String.format("Something went wrong with user id: %d!", userId));
        }
    }

    public void refillRemainingContent() {
        // not a method in the scope of the application, but might be fun to implement in the future.
    }
}
