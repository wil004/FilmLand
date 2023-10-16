package com.sogeti.filmland.category.user;

import com.sogeti.filmland.category.Category;
import com.sogeti.filmland.user.User;

public abstract class UserCategoryDTOConverter {

    public void setUpDTO(UserCategoryDTO dto, UserCategory entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getCategory().getName().getDisplayName());
        dto.setPrice(entity.getCategory().getPrice());
    }

    public void setUpEntity(UserCategory entity, UserCategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getCategoryId());
        entity.setCategory(category);
        User user = new User();
        user.setId(dto.getUserId());
        entity.setUser(user);
    }
}
