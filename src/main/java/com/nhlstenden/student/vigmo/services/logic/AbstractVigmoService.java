package com.nhlstenden.student.vigmo.services.logic;

import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.EntityId;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public abstract class AbstractVigmoService<Repository extends JpaRepository<Entity, Long>, DTO, Entity extends EntityId> implements VigmoService<DTO> {

    protected final Repository repo;
    protected final MappingUtility mapper;
    protected final Class<DTO> dtoType;
    private final Class<Entity> entityType;

    public AbstractVigmoService(Repository repo, MappingUtility mapper, Class<DTO> dto, Class<Entity> entity) {
        this.repo = repo;
        this.mapper = mapper;
        this.dtoType = dto;
        this.entityType = entity;
    }

    @Override
    public List<DTO> getList() {
        return mapper.mapList(repo.findAll(), dtoType);
    }

    @Override
    public DTO get(long id) {
        Entity o = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + id));
        return mapper.mapObject(o, dtoType);
    }

    @Override
    public long create(DTO dto) {
        return repo.save(mapper.mapObject(dto, entityType)).getId();
    }

    @Override
    public void update(DTO dto, long id) {
        repo.findById(id).orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + id));
        Entity newObject = mapper.mapObject(dto, entityType);
        newObject.setId(id);
        repo.save(newObject);
    }

    @Override
    public void delete(long id) {
        repo.findById(id).orElseThrow(() -> new DataNotFoundException(getClass().getSimpleName() + " could not find " + id));
        repo.deleteById(id);
    }
}
