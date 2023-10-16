package com.sogeti.filmland.category.user.subscription.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedSubscriptionRepository extends JpaRepository<SharedSubscription, Integer> {
}
