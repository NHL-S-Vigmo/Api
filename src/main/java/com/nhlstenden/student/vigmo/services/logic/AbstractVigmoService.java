package com.nhlstenden.student.vigmo.services.logic;

import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.dto.SlideshowDto;
import com.nhlstenden.student.vigmo.exception.IdProvidedInCreateRequestException;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.EntityIdRequirementNotMetException;
import com.nhlstenden.student.vigmo.models.EntityId;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import io.jsonwebtoken.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
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
    private final LogService logService;
    private final String ACTION_CREATE;
    private final String ACTION_UPDATE;
    private final String ACTION_DELETE;

    public AbstractVigmoService(Repository repo, MappingUtility mapper, Class<DTO> dto, Class<Entity> entity, LogService logService) {
        this.repo = repo;
        this.mapper = mapper;
        this.dtoType = dto;
        this.entityType = entity;
        this.logService = logService;
        this.ACTION_CREATE = "Create " + entityType.getSimpleName();
        this.ACTION_UPDATE = "Update " + entityType.getSimpleName();
        this.ACTION_DELETE = "Delete " + entityType.getSimpleName();
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
            Long id = getIdFieldValue(dto);

            if (id != null) {
                throw new IdProvidedInCreateRequestException("Id provided in create request", id);
            }
        } catch (NoSuchFieldException e) {
            throw new EntityIdRequirementNotMetException();
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

    @Override
    public final long create(DTO dto, long userId, String username) {
        long id = create(dto);
        LogDto logDto = new LogDto(userId, username, ACTION_CREATE, "", Instant.now().getEpochSecond());

        if (logService != null)
            logService.create(logDto);
        return id;
    }

    @Override
    public final void update(DTO dto, long id, long userId, String username) {
        update(dto, id);
        LogDto logDto = new LogDto(userId, username, ACTION_UPDATE, "", Instant.now().getEpochSecond());
        if (logService != null)
            logService.create(logDto);
    }

    @Override
    public final void delete(long id, long userId, String username) {
        delete(id);
        LogDto logDto = new LogDto(userId, username, ACTION_DELETE, "", Instant.now().getEpochSecond());
        if (logService != null)
            logService.create(logDto);
    }

    /**
     * Function checks if the provided object has a field names 'id'
     *
     * @param object generic object
     * @return returns the id that was in the object
     * @throws NoSuchFieldException will throw when field id is not present in object
     */
    private Long getIdFieldValue(DTO object) throws NoSuchFieldException {
        //get the field from the initial object
        Optional<Field> field = Arrays.stream(object.getClass().getDeclaredFields()).filter(f -> f.getName().equals("id")).findFirst();
        if (field.isPresent()) {
            return getIdGetterResult(object);
        } else {
            //if the field was not found, try again with the generic super class.
            Optional<Field> field1 = Arrays.stream(((Class) object.getClass().getGenericSuperclass()).getDeclaredFields()).filter(f -> f.getName().equals("id")).findFirst();

            if (field1.isPresent()) {
                return getIdGetterResult(object);
            }
        }

        throw new NoSuchFieldException("Field id was not found on the object.");
    }

    /**
     * Function that executes the getId on anonymous objects
     *
     * @param object dto object to get the id from
     * @return returns -1 if there is no id present
     */
    public Long getIdGetterResult(DTO object) {
        Optional<Method> getter = Arrays.stream(object.getClass().getMethods()).filter(method -> method.getName().equals("getId")).findFirst();

        if (getter.isEmpty()) {
            return null;
        }

        Method get = getter.get();
        try {
            Object result = get.invoke(object);

            if (result == null) return null;
            else return (Long) result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Checks if the current authentication object contains the provided authority.
     * @param authority the role to verify
     * @return returns true if the user has the authority, returns false if there is no authentication object set.
     */
    public boolean userHasAuthority(String... authority){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getAuthorities().size() == 0) return false;

        return Arrays.stream(authority).anyMatch(s -> authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(s)));
    }

    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
