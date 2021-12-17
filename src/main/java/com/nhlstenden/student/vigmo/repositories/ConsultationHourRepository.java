package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.ConsultationHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationHourRepository extends JpaRepository<ConsultationHour, Long> {
}
