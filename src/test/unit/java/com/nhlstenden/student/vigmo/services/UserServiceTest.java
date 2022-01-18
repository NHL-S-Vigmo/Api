package unit.java.com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.services.UserService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

    @BeforeEach
    void setup() {
        openMocks(this);

        //Encrypted password string currently in database for the user
        oldPasswordString = "$2a$10$9yiz1wi41mdOTLDNeDiMqOAobStwnR4SAaLQQ6TBPOofKgT1ObOv2";
    }

    @Test
    void testFindExistingUsername() {
        when(repo.findByUsername(anyString())).thenReturn(Optional.of(userEntityMock));
        when(mapper.mapObject(any(User.class), eq(UserDto.class))).thenReturn(userDtoMock);

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

    @Test
    void testUpdatePassword(){
        //Encrypted string of the new password
        String newPasswordString ="$2y$10$esv7TJOyfROYADjv08Ba5OasbZLkpZuHfvWaNAlRiQb42P8t1ujs.";

        User user = new User(1L, "TestUser", oldPasswordString,true, "ROLE_ADMIN", "image_103");
        UserDto userDto = new UserDto(1L, "TestUser","password123",true, "ROLE_ADMIN", "image_103");

        when(passwordEncoderMock.encode(any())).thenReturn(newPasswordString);
        when(repo.findById(anyLong())).thenReturn(Optional.of(user));
        when(mapper.mapObject(any(UserDto.class), eq(User.class))).thenReturn(user);

        //Test updating the password
        userService.update(userDto, 1L);
        assertThat(userDto.getPassword()).isEqualTo(newPasswordString);

        //verify object was saved in the database
        verify(repo).save(any(User.class));
    }

    @Test
    void testUpdateWithNoPassword(){
        User user = new User(1L, "TestUser", oldPasswordString,true, "ROLE_ADMIN", "image_103");
        UserDto userDto = new UserDto(1L, "TestUser","",true, "ROLE_ADMIN", "image_103");

        when(repo.findById(anyLong())).thenReturn(Optional.of(user));
        when(mapper.mapObject(any(UserDto.class), eq(User.class))).thenReturn(user);

        userService.update(userDto, 1L);
        //Password after updating should remain the same as the password currently in the database
        assertThat(userDto.getPassword()).isEqualTo(oldPasswordString);
        //verify object was saved in the database
        verify(repo).save(any(User.class));
    }
}
