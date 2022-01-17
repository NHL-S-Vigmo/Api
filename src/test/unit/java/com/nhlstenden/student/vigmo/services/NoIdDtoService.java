package unit.java.com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.services.LogService;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import unit.java.com.nhlstenden.student.vigmo.dto.NoIdEntityDto;
import unit.java.com.nhlstenden.student.vigmo.models.NoIdEntity;
import unit.java.com.nhlstenden.student.vigmo.repositories.NoIdEntityRepository;

public class NoIdDtoService extends AbstractVigmoService<NoIdEntityRepository, NoIdEntityDto, NoIdEntity> {
    public NoIdDtoService(NoIdEntityRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, NoIdEntityDto.class, NoIdEntity.class, logService);
    }
}