package com.nhlstenden.student.vigmo.services.logic;

import com.nhlstenden.student.vigmo.exception.DataAlreadyExistsException;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.EntityId;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        try {
            long id = isFieldSet(dto);
            //todo: check if this id is already in the DB, otherwise it will overwrite that object.
            Optional<Entity> entity = repo.findById(id);

            if(entity.isPresent()) {
                throw new DataAlreadyExistsException("Object has already been found in the database.", id);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

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

    private long isFieldSet(DTO object) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField("id");
        return getGetterResult(field, dtoType, object);
    }

    /**
     * Function that executes the getId on anonymous objects
     * @param field
     * @param o
     * @param object
     * @return returns -1 if there is no id present
     */
    public long getGetterResult(Field field, Class<DTO> o, DTO object)
    {
        Optional<Method> getter =  Arrays.stream(o.getMethods()).filter(method -> method.getName().equals("getId")).findFirst();

        if(!getter.isPresent()){
            return -1;
        }

        Method get = getter.get();
        try
        {
            return (Long) get.invoke(object);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            return -1;
        }
    }
}
