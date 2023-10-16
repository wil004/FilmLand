package com.sogeti.filmland.category.user.available;

import com.sogeti.filmland.category.user.UserCategory;
import com.sogeti.filmland.category.user.UserCategoryDTOConverter;
import com.sogeti.filmland.utils.DTOConverter;

public class AvailableUserCategoryDTOConverter extends UserCategoryDTOConverter
        implements DTOConverter<UserCategory, AvailableUserCategoryDTO> {

    @Override
    public AvailableUserCategoryDTO toDTO(UserCategory entity) {
        AvailableUserCategoryDTO dto = new AvailableUserCategoryDTO();
        super.setUpDTO(dto, entity);
        dto.setAvailableContent(entity.getCategory().getAvailableContent());
        return dto;
    }

    @Override
    public UserCategory mergeToEntity(UserCategory entity, AvailableUserCategoryDTO dto) {
        throw new UnsupportedOperationException("Merge to entity currently not operational for AvailableUserCategory");
    }
}
