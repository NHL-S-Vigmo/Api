package com.nhlstenden.student.vigmo.controllers.logic;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public interface VigmoController<T> {
    @GetMapping
    ResponseEntity<List<T>> get();

    @GetMapping("/{id}")
    ResponseEntity<T> get(@PathVariable("id") final long id);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> post(@Valid @RequestBody T postObject, Principal principal);

    @PutMapping("/{id}")
    ResponseEntity<Void> put(@PathVariable("id") final long id, @Valid @RequestBody T putObject, Principal principal);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") final long id, Principal principal);
}
