package com.nhlstenden.student.vigmo.testEntity;

import com.nhlstenden.student.vigmo.models.EntityId;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestEntity implements EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
