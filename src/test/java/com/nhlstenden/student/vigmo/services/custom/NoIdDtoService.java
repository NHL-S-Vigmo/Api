package com.nhlstenden.student.vigmo.services.custom;

import com.nhlstenden.student.vigmo.dto.NoIdEntityDto;
import com.nhlstenden.student.vigmo.models.NoIdEntity;
import com.nhlstenden.student.vigmo.repositories.NoIdEntityRepository;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;

public class NoIdDtoService extends AbstractVigmoService<NoIdEntityRepository, NoIdEntityDto, NoIdEntity> {
    public NoIdDtoService(NoIdEntityRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, NoIdEntityDto.class, NoIdEntity.class, logService);
    }
}