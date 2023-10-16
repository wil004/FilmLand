package com.sogeti.filmland.category;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private CategoryType name;

    private BigDecimal price;

    private int availableContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryType getName() {
        return name;
    }

    public void setName(CategoryType name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAvailableContent() {
        return availableContent;
    }

    public void setAvailableContent(int availableContent) {
        this.availableContent = availableContent;
    }
}
