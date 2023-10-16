package com.sogeti.filmland.category.user;

import javax.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UserCategoryId {

    private Integer userId;

    private Integer categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCategoryId that = (UserCategoryId) o;
        return Objects.equals(((UserCategoryId) o).userId, that.userId) &&
                Objects.equals(((UserCategoryId) o).categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, categoryId);
    }
}
