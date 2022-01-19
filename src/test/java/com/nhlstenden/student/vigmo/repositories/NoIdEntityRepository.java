package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.NoIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoIdEntityRepository extends JpaRepository<NoIdEntity, Long> {
}
