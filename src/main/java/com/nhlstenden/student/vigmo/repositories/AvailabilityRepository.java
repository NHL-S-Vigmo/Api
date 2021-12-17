package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
