package com.nhlstenden.student.vigmo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public interface VigmoController<T> {
    @GetMapping
    ResponseEntity<List<T>> get();

    @GetMapping("/{id}")
    ResponseEntity<T> get(@PathVariable("id") final long id);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> post(@Valid T postObject);

    @PutMapping("/{id}")
    ResponseEntity<Void> put(@PathVariable("id") final long id,@Valid T putObject);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") final long id);
}
