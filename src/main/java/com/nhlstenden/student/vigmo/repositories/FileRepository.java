package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByKey(String fileKey);
}
