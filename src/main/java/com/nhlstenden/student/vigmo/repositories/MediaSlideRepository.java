package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.MediaSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaSlideRepository extends JpaRepository<MediaSlide, Long> {
}
