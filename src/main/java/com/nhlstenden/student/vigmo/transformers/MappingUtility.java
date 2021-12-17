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

    public <S, T> List<T> mapList(List<S> source, Class<T> target) {
        return source.stream().map(element -> modelMapper.map(element, target)).collect(Collectors.toList());
    }

    public <S, T> T mapObject(S source, Class<T> target){
      return modelMapper.map(source, target);
    };
}
