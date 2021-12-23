package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}
