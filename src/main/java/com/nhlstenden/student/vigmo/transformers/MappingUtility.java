package com.nhlstenden.student.vigmo.transformers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingUtility {
    private final ModelMapper modelMapper;

    public MappingUtility(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Transforms the {@link S source} list into the {@link T target} class type.
     * It works by iterating over each object.
     * @param source the object to transform
     * @param target the class to transform the object to
     * @param <S> Source object type
     * @param <T> Target object Type
     * @return Transformed {@link List<T> List<T>} object
     */
    public <S, T> List<T> mapList(List<S> source, Class<T> target) {
        return source.stream().map(element -> modelMapper.map(element, target)).collect(Collectors.toList());
    }

    /**
     * Transforms the {@link S source} object into the {@link T target} class type
     * @param source the object to transform
     * @param target the class to transform the object to
     * @param <S> Source object type
     * @param <T> Target object Type
     * @return Transformed object
     */
    public <S, T> T mapObject(S source, Class<T> target) {
        return modelMapper.map(source, target);
    }
}
