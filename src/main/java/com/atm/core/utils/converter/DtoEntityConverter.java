package com.atm.core.utils.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DtoEntityConverter {
    private ModelMapper modelMapper ;
    @Autowired
    public DtoEntityConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public Object entityToDto(Object entitiy, Object dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(entitiy, dto.getClass());
    }

    public Object dtoToEntity(Object dto, Object entity){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, entity.getClass());
    }

    public List<Object> dtosToEntities(List<Object> dtos, Object entity){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Object> entities = new ArrayList<>();
        dtos.forEach(dto -> entities.add(modelMapper.map(dto, entity.getClass())));
        return entities;
    }

    public List<Object> entitiesToDtos(List<Object> entities, Object dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<Object> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(modelMapper.map(entity, dto.getClass())));
        return dtos;
    }
}
