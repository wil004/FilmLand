package com.sogeti.filmland.category.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Integer> {

    List<UserCategory> findAllByUserIdAndStartDateIsNull(Integer customerId);

    List<UserCategory> findAllByUserIdAndStartDateNotNull(Integer customerId);

    Optional<UserCategory> findByUserIdAndCategoryIdAndStartDateIsNull(Integer userId, Integer categoryId);

    Optional<UserCategory> findByUserIdAndCategoryIdAndStartDateNotNull(Integer userId, Integer categoryId);

    @Query(value = "SELECT uc FROM UserCategory uc " +
            "WHERE uc.startDate is NOT NULL " +
            "AND DATEDIFF(month, uc.startDate, CURRENT_DATE) > 2 ")
    List<UserCategory> getSubscriptionsOlderThan2Months();

    @Query(value = "SELECT uc FROM UserCategory uc " +
            "WHERE uc.startDate is NOT NULL " +
            "AND DATEDIFF(month, uc.startDate, CURRENT_DATE) > 1 ")
    List<UserCategory> getSubscriptionsOlderThan1Month();
}
