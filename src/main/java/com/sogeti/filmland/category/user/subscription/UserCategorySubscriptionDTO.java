package com.sogeti.filmland.category.user.subscription;

import com.sogeti.filmland.category.user.UserCategoryDTO;

import java.time.LocalDate;

public class UserCategorySubscriptionDTO extends UserCategoryDTO {

    private Integer userId;

    private int remainingContent;

    private LocalDate startDate;

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getRemainingContent() {
        return remainingContent;
    }

    public void setRemainingContent(int remainingContent) {
        this.remainingContent = remainingContent;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
