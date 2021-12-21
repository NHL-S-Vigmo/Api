package com.nhlstenden.student.vigmo.controllers.logic;

import com.nhlstenden.student.vigmo.services.logic.VigmoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.List;

public abstract class AbstractVigmoController<Service extends VigmoService<DTO>, DTO> implements VigmoController<DTO> {

    private final Service service;

    public AbstractVigmoController(Service service) {
        this.service = service;
    }

    @Override
    @ApiOperation(value = "Gets list of objects in the database")
    public ResponseEntity<List<DTO>> get() {
        return ResponseEntity.ok(service.getList());
    }

    @ApiOperation(value = "Gets a specific object")
    @Override
    public ResponseEntity<DTO> get(final long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @ApiOperation(value = "Creates a new object")
    @Override
    public ResponseEntity<Void> post(DTO postObject) {
        return ResponseEntity.created(URI.create(String.format("/%s/%d", getPathName(), service.create(postObject)))).build();
    }

    @ApiOperation(value = "Updates an existing object in the database")
    @Override
    public ResponseEntity<Void> put(final long id, DTO putObject) {
        service.update(putObject, id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Deletes a object from the database")
    @Override
    public ResponseEntity<Void> delete(final long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private String getPathName() {
        String[] annotationValues = getClass().getAnnotation(RequestMapping.class).value();
        return String.join("", annotationValues);
    }
}
