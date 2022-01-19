package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.Slideshow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideshowRepository extends JpaRepository<Slideshow, Long> {
    List<Slideshow> findSlideshowsByScreenName(String screenName);
}
