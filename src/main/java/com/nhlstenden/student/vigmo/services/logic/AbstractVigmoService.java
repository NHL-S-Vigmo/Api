package com.nhlstenden.student.vigmo.services.logic;

import com.nhlstenden.student.vigmo.exception.IdProvidedInCreateRequestException;
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

            if (id != -1) {
                //Optional<Entity> entity = repo.findById(id);
                throw new IdProvidedInCreateRequestException("Id provided in create request", id);
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

    /**
     * Function checks if the provided object has a field names 'id'
     *
     * @param object generic object
     * @return returns the id that was in the object
     * @throws NoSuchFieldException
     */
    private long isFieldSet(DTO object) throws NoSuchFieldException {
        //get the field from the initial object
        Optional<Field> field = Arrays.stream(object.getClass().getDeclaredFields()).filter(f -> f.getName().equals("id")).findFirst();
        if (field.isPresent()) {
            return getGetterResult(dtoType, object);
        } else {
            //if the field was not found, try again with the generic super class.
            Optional<Field> field1 = Arrays.stream(((Class) object.getClass().getGenericSuperclass()).getDeclaredFields()).filter(f -> f.getName().equals("id")).findFirst();

            if (field1.isPresent()) {
                return getGetterResult(dtoType, object);
            }
        }

        throw new NoSuchFieldException("Field id was not found on the object.");
    }

    /**
     * Function that executes the getId on anonymous objects
     *
     * @param o
     * @param object
     * @return returns -1 if there is no id present
     */
    public long getGetterResult(Class<DTO> o, DTO object) {
        Optional<Method> getter = Arrays.stream(o.getMethods()).filter(method -> method.getName().equals("getId")).findFirst();

        if (!getter.isPresent()) {
            return -1;
        }

        Method get = getter.get();
        try {
            Object result = get.invoke(object);

            if (result == null) return -1;
            else return (Long) result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            return -1;
        }
    }
}
