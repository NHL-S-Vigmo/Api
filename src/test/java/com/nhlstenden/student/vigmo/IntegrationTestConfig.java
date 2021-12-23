package com.nhlstenden.student.vigmo;

import com.nhlstenden.student.vigmo.dto.TestEntityDto;
import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.services.TestEntityService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan(basePackages = "com.nhlstenden.student.vigmo", excludeFilters = {
        @Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = {TestEntityDto.class, TestEntity.class,
                        TestEntityService.class, TestEntityRepository.class})})
public class IntegrationTestConfig extends TestApplicationContext {

}
