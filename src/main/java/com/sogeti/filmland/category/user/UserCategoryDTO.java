package com.sogeti.filmland.category.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public abstract class UserCategoryDTO {

    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer categoryId;

    @JsonIgnore
    private Integer userId;

    private String name;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
