package com.nhlstenden.student.vigmo.it;

import com.nhlstenden.student.vigmo.dto.NoIdEntityDto;
import com.nhlstenden.student.vigmo.models.NoIdEntity;
import com.nhlstenden.student.vigmo.models.TestEntity;
import com.nhlstenden.student.vigmo.repositories.NoIdEntityRepository;
import com.nhlstenden.student.vigmo.repositories.TestEntityRepository;
import com.nhlstenden.student.vigmo.services.custom.NoIdDtoService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.nhlstenden.student.vigmo.TestApplicationContext;
import com.nhlstenden.student.vigmo.dto.TestEntityDto;
import com.nhlstenden.student.vigmo.services.custom.TestEntityService;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.nhlstenden.student.vigmo", excludeFilters = {
        //Exclude custom classes used only in unit tests
        @Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = {TestEntityDto.class, TestEntity.class,
                        TestEntityService.class, TestEntityRepository.class,
                        NoIdEntityDto.class, NoIdEntity.class,
                        NoIdEntityRepository.class, NoIdDtoService.class})})
public class IntegrationTestConfig extends TestApplicationContext {

}
