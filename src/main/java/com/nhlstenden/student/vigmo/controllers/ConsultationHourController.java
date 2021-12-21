package com.nhlstenden.student.vigmo.controllers;

import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.services.ConsultationHourService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("Consultation Hour Controller")
@RestController
@RequestMapping("consultation_hour")
public class ConsultationHourController implements VigmoController<ConsultationHourDto> {

    private final ConsultationHourService service;

    public ConsultationHourController(ConsultationHourService service){
        this.service = service;
    }

    @Override
    public ResponseEntity<List<ConsultationHourDto>> get() {
        return ResponseEntity.ok(service.getList());
    }

    @Override
    public ResponseEntity<ConsultationHourDto> get(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> post(ConsultationHourDto postObject) {
        return null;
    }

    @Override
    public ResponseEntity<Void> put(long id, ConsultationHourDto putObject) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(long id) {
        return null;
    }
}
