package com.sogeti.filmland.category.user.subscription;

import com.sogeti.filmland.category.user.UserCategory;
import com.sogeti.filmland.category.user.UserCategoryDTOConverter;
import com.sogeti.filmland.user.User;
import com.sogeti.filmland.utils.DTOConverter;

import java.time.LocalDate;

public class UserCategorySubscriptionDTOConverter extends UserCategoryDTOConverter
        implements DTOConverter<UserCategory, UserCategorySubscriptionDTO> {

    @Override
    public UserCategorySubscriptionDTO toDTO(UserCategory entity) {
        UserCategorySubscriptionDTO dto = new UserCategorySubscriptionDTO();
        super.setUpDTO(dto, entity);
        dto.setUserId(entity.getUser().getId());
        dto.setStartDate(entity.getStartDate());
        dto.setRemainingContent(entity.getRemainingContent());
        return dto;
    }

    @Override
    public UserCategory mergeToEntity(UserCategory entity, UserCategorySubscriptionDTO dto) {
        super.setUpEntity(entity, dto);
        User user = new User();
        user.setId(dto.getUserId());
        entity.setUser(user);
        entity.setStartDate(LocalDate.now());
        entity.setRemainingContent(dto.getRemainingContent());
        return entity;
    }
}
