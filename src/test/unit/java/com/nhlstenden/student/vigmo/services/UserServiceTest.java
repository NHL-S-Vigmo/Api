package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    User userEntityMock;

    @Mock
    UserDto userDtoMock;

    @Mock
    private MappingUtility mapper;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void findByUsername() {
        //todo: add mockito verify checks
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.of(userEntityMock));
        when(mapper.mapObject(any(User.class), eq(UserDto.class))).thenReturn(userDtoMock);

        UserDto user = userService.findByUsername("username");

        assertThat(user)
                .isNotNull();
    }
}