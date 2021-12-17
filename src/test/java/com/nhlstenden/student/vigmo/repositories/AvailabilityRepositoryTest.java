package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.TestApplicationContext;
import com.nhlstenden.student.vigmo.dto.MediaSlideDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestApplicationContext.class)
public class AvailabilityRepositoryTest {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @PersistenceContext
    private EntityManager entityManager;

}