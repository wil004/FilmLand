package com.sogeti.filmland.category.user;

import com.sogeti.filmland.category.user.available.AvailableUserCategoryDTO;
import com.sogeti.filmland.category.user.subscription.UserCategorySubscriptionDTO;

import java.util.List;

public class UserCategoriesDTO {

    List<AvailableUserCategoryDTO> availableCategories;

    List<UserCategorySubscriptionDTO> subScribedCategories;

    public List<AvailableUserCategoryDTO> getAvailableCategories() {
        return availableCategories;
    }

    public void setAvailableCategories(List<AvailableUserCategoryDTO> availableCategories) {
        this.availableCategories = availableCategories;
    }

    public List<UserCategorySubscriptionDTO> getSubScribedCategories() {
        return subScribedCategories;
    }

    public void setSubScribedCategories(List<UserCategorySubscriptionDTO> subScribedCategories) {
        this.subScribedCategories = subScribedCategories;
    }
}
