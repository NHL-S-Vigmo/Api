package com.nhlstenden.student.vigmo.controllers.logic;

import com.nhlstenden.student.vigmo.services.logic.VigmoService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin(exposedHeaders = "X-Total-Count, Location, jwt-new-token, X-File-Id")
public abstract class AbstractVigmoController<Service extends VigmoService<DTO>, DTO> implements VigmoController<DTO> {
    protected final Service service;

    public AbstractVigmoController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Service service) {
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
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    @Override
    public ResponseEntity<Void> post(@Valid DTO postObject, Authentication authentication) {
        String username = authentication.getName();
        Long userId = getIdFromToken();
        return ResponseEntity.created(URI.create(String.format("/%s/%d", getPathName(), service.create(postObject, userId, username)))).build();
    }

    @ApiOperation(value = "Updates an existing object in the database")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Bad request")})
    @Override
    public ResponseEntity<Void> put(final long id, @Valid DTO putObject, Authentication authentication) {
        String username = authentication.getName();

        Long userId = getIdFromToken();
        service.update(putObject, id, userId, username);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Deletes an object from the database")
    @Override
    public ResponseEntity<Void> delete(final long id, Authentication authentication) {
        String username = authentication.getName();
        Long userId = getIdFromToken();
        service.delete(id, userId, username);
        return ResponseEntity.noContent().build();
    }

    private String getPathName() {
        String[] annotationValues = getClass().getAnnotation(RequestMapping.class).value();
        return String.join("", annotationValues);
    }

    private Long getIdFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getCredentials() == null || authentication.getCredentials() instanceof String) return null;
        Claims claims = (Claims) authentication.getCredentials();
        return claims.get("id", Long.class);
    }
}
