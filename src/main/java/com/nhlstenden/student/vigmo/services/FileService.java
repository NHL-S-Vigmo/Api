package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.FileDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.File;
import com.nhlstenden.student.vigmo.repositories.FileRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService extends AbstractVigmoService<FileRepository, FileDto, File> {
    public FileService(FileRepository repo, MappingUtility mapper, LogService logService) {
        super(repo, mapper, FileDto.class, File.class, logService);
    }

    @Override
    public List<FileDto> getList() {
        List<FileDto> returnList = super.getList();

        for(FileDto file: returnList){
            file.setData("<removed-binary-data>");
        }

        return returnList;
    }

    @Override
    public FileDto get(long id) {
        FileDto file = super.get(id);
        file.setData("<removed-binary-data>");
        return file;
    }

    @Override
    public long create(FileDto fileDto) {
        fileDto.setKey(generateRandom64LengthKey());
        return super.create(fileDto);
    }

    @Override
    public void update(FileDto fileDto, long id) {
        //get the existing key using the get function to inherit not found logic.
        fileDto.setKey(this.get(id).getKey());
        super.update(fileDto, id);
    }

    public File getRawEntityByKey(String fileKey) {
        return repo.findByKey(fileKey).orElseThrow(() ->
                new DataNotFoundException(getClass().getSimpleName() + " could not find " + fileKey));
    }

    protected String generateRandom64LengthKey() {
        int length = 64;
        return RandomStringUtils.random(length, true, true);
    }
}
