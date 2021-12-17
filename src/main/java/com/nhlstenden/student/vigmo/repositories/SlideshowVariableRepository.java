package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.SlideshowVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideshowVariableRepository extends JpaRepository<SlideshowVariable, Long> {
}
