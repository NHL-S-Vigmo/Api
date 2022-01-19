package com.nhlstenden.student.vigmo.services.custom;

import com.nhlstenden.student.vigmo.dto.TestEntityDto;
import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class TestEntityService extends AbstractVigmoService<TestEntityRepository, TestEntityDto, TestEntity> {
    public TestEntityService(TestEntityRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, TestEntityDto.class, TestEntity.class, logService);
    }
}
