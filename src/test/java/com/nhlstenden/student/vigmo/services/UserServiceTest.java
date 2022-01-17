package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Mock
    PasswordEncoder passwordEncoderMock;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void findByUsername() {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(Optional.of(userEntityMock));
        when(mapper.mapObject(any(User.class), eq(UserDto.class))).thenReturn(userDtoMock);

        UserDto user = userService.findByUsername("username");

        assertThat(user)
                .isNotNull();
    }

    @Test
    void updatePassword(){
        String oldPasswordString = "$2a$10$9yiz1wi41mdOTLDNeDiMqOAobStwnR4SAaLQQ6TBPOofKgT1ObOv2";
        String newPasswordString ="$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.";

        User user = new User(1L, "TestUser", oldPasswordString,true, "ROLE_ADMIN", "image_103");
        UserDto userDto = new UserDto(1L, "TestUser","",true, "ROLE_ADMIN", "image_103");

        when(passwordEncoderMock.encode(any())).thenReturn(newPasswordString);
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(user));
        when(mapper.mapObject(any(UserDto.class), eq(User.class))).thenReturn(user);

        //Test updating without a password set (keeping the password already in the database)
        userService.update(userDto, 1L);
        assertThat(userDto.getPassword()).isEqualTo(oldPasswordString);

        //Test updating the password
        userDto.setPassword("password123");
        userService.update(userDto, 1L);
        assertThat(userDto.getPassword()).isEqualTo(newPasswordString);


    }
}