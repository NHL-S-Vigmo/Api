package com.nhlstenden.student.vigmo.testEntity;

import com.nhlstenden.student.vigmo.services.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.stereotype.Service;

@Service
public class TestEntityService  extends AbstractVigmoService<TestEntityRepository, TestEntityDto, TestEntity> {
    public TestEntityService(TestEntityRepository repo, MappingUtility mapper) {
        super(repo, mapper, TestEntityDto.class, TestEntity.class);
    }
}
