package com.nhlstenden.student.vigmo.controllers.logic;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
public interface VigmoController<T> {
    @GetMapping
    ResponseEntity<List<T>> get();

    @GetMapping("/{id}")
    ResponseEntity<T> get(@PathVariable("id") final long id);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> post(@Valid @RequestBody T postObject, @ApiIgnore Authentication authentication);

    @PutMapping("/{id}")
    ResponseEntity<Void> put(@PathVariable("id") final long id, @Valid @RequestBody T putObject, @ApiIgnore Authentication authentication);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") final long id, @ApiIgnore Authentication authentication);
}
