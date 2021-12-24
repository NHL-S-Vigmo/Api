package com.nhlstenden.student.vigmo.services.logic;

import java.util.List;

public interface VigmoService<DtoObject> {
    List<DtoObject> getList();
    DtoObject get(long id);
    long create(DtoObject dto);
    void update(DtoObject dto, long id);
    void delete(long id);
}
