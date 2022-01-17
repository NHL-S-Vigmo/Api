package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Optional<Screen> findByAuthKey(String authKey);
}
