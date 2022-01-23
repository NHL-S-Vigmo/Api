package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.UserAlreadyExistsException;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    UserRepository repo;

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

    String oldPasswordString;
    String newPasswordString;
    @Mock
    User userMock;

    @BeforeEach
    void setup() {
        openMocks(this);

        //Encrypted string of the new password
         newPasswordString ="$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.";

        //Encrypted password string currently in database for the user
        oldPasswordString = "$2a$10$9yiz1wi41mdOTLDNeDiMqOAobStwnR4SAaLQQ6TBPOofKgT1ObOv2";

        //Mock password functions
        when(passwordEncoderMock.encode(any())).thenReturn(newPasswordString);
        when(userMock.getPassword()).thenReturn(oldPasswordString);

        //Mock repository functions
        when(repo.findById(anyLong())).thenReturn(Optional.of(userEntityMock));
        when(repo.save(userMock)).thenReturn(userMock);

        //Make sure the id is not set in dto and is set in de entity
        when(userDtoMock.getId()).thenReturn(null);
        when(userMock.getId()).thenReturn(1L);

        //Mock mapper functions
        when(mapper.mapObject(any(User.class), eq(UserDto.class))).thenReturn(userDtoMock);
        when(mapper.mapObject(any(UserDto.class), eq(User.class))).thenReturn(userMock);
    }

    @Test
    void testGetList(){
        //Make the mapper return a list with a user with the password set
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDtoMock);

        when(repo.findAll()).thenReturn(new ArrayList<>());
        when(mapper.mapList(anyList(), eq(UserDto.class))).
                thenReturn(userDtoList);

        userService.getList();

        verify(repo).findAll();
        verify(mapper).mapList(anyList(), eq(UserDto.class));
        //verify that the password has been set to an empty string
        verify(userDtoMock).setPassword("");
    }

    @Test
    void testGet(){
        userService.get(1L);

        verify(repo).findById(anyLong());
        verify(mapper).mapObject(any(User.class), eq(UserDto.class));
        //verify that the password has been set to an empty string
        verify(userDtoMock).setPassword("");
    }

    @Test
    void testUpdatePassword(){
        User userObject = spy(new User());
        userObject.setUsername("Jan_Doornbos");
        doReturn(Optional.of(userObject)).when(repo).findById(any());

        when(userDtoMock.getPassword()).thenReturn("password123");
        //Test updating the password
        userService.update(userDtoMock, 1L);

        //verify object was saved in the database
        verify(repo).save(any(User.class));
        verify(userDtoMock).setPassword(newPasswordString);
    }

    @Test
    void testUpdateWithNoPassword(){
        User userObject = spy(new User());
        userObject.setUsername("Jan_Doornbos");
        doReturn(Optional.of(userObject)).when(repo).findById(any());

        when(userObject.getPassword()).thenReturn(oldPasswordString);
        when(userDtoMock.getPassword()).thenReturn("");

        userService.update(userDtoMock, 1L);
        //verify object was saved in the database
        verify(repo).save(any(User.class));
        //Password after updating should remain the same as the password currently in the database
        verify(userDtoMock).setPassword(oldPasswordString);
    }

    @Test
    void testCreate(){
        when(repo.findByUsername(anyString())).thenReturn(Optional.empty());

        userService.create(userDtoMock);
        //verify object was saved in the database
        verify(repo).save(any(User.class));
    }

    @Test
    void testCreateWithDuplicateUsername(){
        when(repo.findByUsername(any())).thenReturn(Optional.of(userMock));
        assertThatThrownBy(() -> userService.create(userDtoMock)).isInstanceOf(UserAlreadyExistsException.class);
        //verify object was not saved in the database
        verify(repo, Mockito.never()).save(any(User.class));
    }

    @Test
    void testFindExistingUsername() {
        when(repo.findByUsername(anyString())).thenReturn(Optional.of(userEntityMock));

        UserDto user = userService.findByUsername("username");

        assertThat(user)
                .isNotNull();

        verify(repo).findByUsername(anyString());
    }

    @Test
    void testFindNonExistingUsername() {
        when(repo.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->userService.findByUsername("username")).isInstanceOf(DataNotFoundException.class);

        verify(repo).findByUsername(anyString());
    }
}
