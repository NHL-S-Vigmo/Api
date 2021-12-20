package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.ScreenDto;
import com.nhlstenden.student.vigmo.models.Screen;
import com.nhlstenden.student.vigmo.services.ScreenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("screens")
public class ScreenController {
    private ScreenService screenService;

    public ScreenController(ScreenService service){
        this.screenService = service;
    }

    @GetMapping
    public ResponseEntity<List<ScreenDto>> get(){
        return ResponseEntity.ok(screenService.getScreens());
    }
}
