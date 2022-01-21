package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.ConsultationHourDto;
import com.nhlstenden.student.vigmo.models.ConsultationHour;
import com.nhlstenden.student.vigmo.repositories.ConsultationHourRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class ConsultationHourService extends AbstractVigmoService<ConsultationHourRepository, ConsultationHourDto, ConsultationHour> {

    public ConsultationHourService(ConsultationHourRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, ConsultationHourDto.class, ConsultationHour.class, logService);
    }

}
