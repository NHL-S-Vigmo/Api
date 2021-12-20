package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.services.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("screens")
public class ScreenController implements VigmoController<ScreenDto> {
    private final ScreenService screenService;

    public ScreenController(ScreenService service) {
        this.screenService = service;
    }

    @Override
    public ResponseEntity<List<ScreenDto>> get() {
        return ResponseEntity.ok(screenService.getScreens());
    }

    @Override
    public ResponseEntity<ScreenDto> get(final long id) {
        return ResponseEntity.ok(screenService.getScreen(id));
    }

    @Override
    public ResponseEntity<Void> post(ScreenDto postObject) {
        return ResponseEntity.created(URI.create(String.format("/screens/%d", screenService.createScreen(postObject)))).build();
    }

    @Override
    public ResponseEntity<Void> put(final long id, ScreenDto putObject) {
        screenService.updateScreen(putObject, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> delete(final long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.noContent().build();
    }
}
