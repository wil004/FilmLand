package com.sogeti.filmland.category;

public enum CategoryType {

    DUTCH_FILMS("Dutch Films"),
    DUTCH_SERIES("Dutch Series"),
    INTERNATIONAL_FILMS("International Films");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
