package com.epam.course.dataengeneering.data.mapper;

import com.epam.course.dataengeneering.model.Stuff;

import java.util.List;

@FunctionalInterface
public interface ToEntityMapper {
    Stuff map(List<String> entityData);
}
