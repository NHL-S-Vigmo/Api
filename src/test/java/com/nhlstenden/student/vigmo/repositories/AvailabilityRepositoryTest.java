package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.TestApplicationContext;
import com.nhlstenden.student.vigmo.models.Availability;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringJUnitConfig(TestApplicationContext.class)
@Transactional
public class AvailabilityRepositoryTest {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindAll(){
        List<Availability> availabilities = availabilityRepository.findAll();
        assertEquals(12, availabilities.size());

    }
}