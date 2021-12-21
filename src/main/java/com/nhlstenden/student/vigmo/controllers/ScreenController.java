package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.services.ScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Api("Screen Controller")
@RestController
@RequestMapping("screens")
public class ScreenController implements VigmoController<ScreenDto> {
    private final ScreenService screenService;

    public ScreenController(ScreenService service) {
        this.screenService = service;
    }

    @Override
    @ApiOperation(value = "Gets list of screens in the database")
    public ResponseEntity<List<ScreenDto>> get() {
        return ResponseEntity.ok(screenService.getList());
    }

    @ApiOperation(value = "Gets a specific screen")
    @Override
    public ResponseEntity<ScreenDto> get(final long id) {
        return ResponseEntity.ok(screenService.get(id));
    }

    @ApiOperation(value = "Creates a new screen")
    @Override
    public ResponseEntity<Void> post(ScreenDto postObject) {
        return ResponseEntity.created(URI.create(String.format("/screens/%d", screenService.create(postObject)))).build();
    }

    @ApiOperation(value = "Updates an existing screen in the database")
    @Override
    public ResponseEntity<Void> put(final long id, ScreenDto putObject) {
        screenService.update(putObject, id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Deletes a screen from the database")
    @Override
    public ResponseEntity<Void> delete(final long id) {
        screenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
