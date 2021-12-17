package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.TextSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextSlideRepository extends JpaRepository<TextSlide, Long> {
}
