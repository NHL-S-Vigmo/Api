package com.nhlstenden.student.vigmo.services.logic;

import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.EntityId;
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
            throw new DataNotFoundException(getClass().getSimpleName() + " could not find " + id);
        }
    }

    @Override
    public long create(DTO dto) {
        return repo.save(mapper.mapObject(dto, entityType)).getId();
    }

    @Override
    public void update(DTO dto, long id) {
        Optional<Entity> dbObject = repo.findById(id);
        if(dbObject.isPresent()){
            Entity newObject = mapper.mapObject(dto, entityType);
            newObject.setId(id);
            repo.save(newObject);
        }else{
            throw new DataNotFoundException(getClass().getName() + " could not find " + id);
        }
    }

    @Override
    public void delete(long id) {
        Optional<Entity> dbObject = repo.findById(id);
        if(dbObject.isPresent()){
            repo.deleteById(id);
        }else{
            throw new DataNotFoundException("ScreenService could not find " + id);
        }
    }
}
