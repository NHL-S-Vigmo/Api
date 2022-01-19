package com.nhlstenden.student.vigmo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NoIdEntity implements EntityId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notAnId;

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }
}
