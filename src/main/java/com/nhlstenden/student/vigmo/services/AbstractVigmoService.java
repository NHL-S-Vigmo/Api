package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.EntityId;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractVigmoService<Repository extends JpaRepository<Entity, Long>, DTO, Entity extends EntityId> implements VigmoService<DTO> {

    private final Repository repo;
    private final MappingUtility mapper;
    private final Class<DTO> dtoType;
    private final Class<Entity> entityType;

    public AbstractVigmoService(Repository repo, MappingUtility mapper, Class<DTO> dto, Class<Entity> entity){
        this.repo = repo;
        this.mapper = mapper;
        this.dtoType =dto;
        this.entityType = entity;
    }

    @Override
    public List<DTO> getList() {
        return mapper.mapList(repo.findAll(), dtoType);
    }

    @Override
    public DTO get(long id) {
        Optional<Entity> dbObject = repo.findById(id);
        if(dbObject.isPresent()){
            return mapper.mapObject(dbObject.get(), dtoType);
        }
        else{
            throw new DataNotFoundException(getClass().getName() + " could not find " + id);
        }
    }

    @Override
    public long create(DTO dto) {
        Entity newObject = mapper.mapObject(dto, entityType);
        repo.save(newObject);
        return newObject.getId();
    }

    @Override
    public void update(DTO dto, long id) {
        Entity newObject = mapper.mapObject(dto, entityType);
        newObject.setId(id);
        repo.save(newObject);
    }

    @Override
    public void delete(long id) {
        repo.deleteById(id);
    }
}
