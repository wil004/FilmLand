package com.sogeti.filmland.category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CategoryTypeConverter implements AttributeConverter<CategoryType, String> {

    @Override
    public String convertToDatabaseColumn(CategoryType categoryType) {
        return categoryType.getDisplayName();
    }

    @Override
    public CategoryType convertToEntityAttribute(String value) {
        for (CategoryType enumValue : CategoryType.values()) {
            if(enumValue.getDisplayName().equalsIgnoreCase(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
