package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.LogDto;
import com.nhlstenden.student.vigmo.models.Log;
import com.nhlstenden.student.vigmo.repositories.LogRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;

public class LogService extends AbstractVigmoService<LogRepository, LogDto, Log> {
    public LogService(LogRepository repo, MappingUtility mapper) {
        super(repo, mapper, LogDto.class, Log.class);
    }
}
