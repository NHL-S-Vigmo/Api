package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.RssSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssSlideRepository extends JpaRepository<RssSlide, Long> {
}
