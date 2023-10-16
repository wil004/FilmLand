package com.sogeti.filmland.category.user;

import com.sogeti.filmland.category.Category;
import com.sogeti.filmland.user.User;
import javax.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;

@Entity
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "categoryId")
    private Category category;

    private int remainingContent;

    private LocalDate startDate;

    private boolean isShared;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }
}
