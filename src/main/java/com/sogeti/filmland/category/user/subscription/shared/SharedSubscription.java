package com.sogeti.filmland.category.user.subscription.shared;

import com.sogeti.filmland.category.user.UserCategory;
import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "subscriptionA_ID", "subscriptionB_ID" }) })
public class SharedSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private UserCategory subscriptionA;

    @ManyToOne
    private UserCategory subscriptionB;

    private LocalDate startDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserCategory getSubscriptionA() {
        return subscriptionA;
    }

    public void setSubscriptionA(UserCategory subscriptionA) {
        this.subscriptionA = subscriptionA;
    }

    public UserCategory getSubscriptionB() {
        return subscriptionB;
    }

    public void setSubscriptionB(UserCategory subscriptionB) {
        this.subscriptionB = subscriptionB;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
