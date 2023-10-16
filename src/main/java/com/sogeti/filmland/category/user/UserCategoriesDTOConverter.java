package com.sogeti.filmland.category.user;

import com.sogeti.filmland.category.user.available.AvailableUserCategoryDTOConverter;
import com.sogeti.filmland.category.user.subscription.UserCategorySubscriptionDTOConverter;

import java.util.List;

public class UserCategoriesDTOConverter {

    private final UserCategorySubscriptionDTOConverter categorySubscriptionDTOConverter =
            new UserCategorySubscriptionDTOConverter();

    private final AvailableUserCategoryDTOConverter availableCategoryDTOConverter =
            new AvailableUserCategoryDTOConverter();

    public UserCategoriesDTO toDTO(List<UserCategory> availableCategories,
                                   List<UserCategory> subscribedCategories) {
        UserCategoriesDTO dto = new UserCategoriesDTO();

        dto.setSubScribedCategories(categorySubscriptionDTOConverter.toDTO(subscribedCategories));
        dto.setAvailableCategories(availableCategoryDTOConverter.toDTO(availableCategories));

        return dto;
    }
}
