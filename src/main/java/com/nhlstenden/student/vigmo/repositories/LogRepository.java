package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
