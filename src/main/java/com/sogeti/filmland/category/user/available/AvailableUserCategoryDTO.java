package com.sogeti.filmland.category.user.available;

import com.sogeti.filmland.category.user.UserCategoryDTO;

public class AvailableUserCategoryDTO extends UserCategoryDTO {

    private int availableContent;

    public int getAvailableContent() {
        return availableContent;
    }

    public void setAvailableContent(int availableContent) {
        this.availableContent = availableContent;
    }
}
