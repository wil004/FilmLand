package com.sogeti.filmland.utils;

import java.util.List;

public interface DTOConverter<E, D> {

    default List<D> toDTO(List<E> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }

    D toDTO(E entity);

    E mergeToEntity(E entity, D dto);
}
