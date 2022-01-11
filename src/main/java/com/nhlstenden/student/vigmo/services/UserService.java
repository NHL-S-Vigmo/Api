package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.exception.DataNotFoundException;
import com.nhlstenden.student.vigmo.exception.UserAlreadyExistsException;
import com.nhlstenden.student.vigmo.models.Slideshow;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.services.logic.AbstractVigmoService;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService extends AbstractVigmoService<UserRepository, UserDto, User> {
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, MappingUtility mapper, PasswordEncoder encoder) {
        super(repo, mapper, UserDto.class, User.class);
        this.encoder = encoder;
    }

    public UserDto findByUsername(String username) {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Could not find user " + username));

        return mapper.mapObject(u, dtoType);
    }

    @Override
    public UserDto get(long id) {
        UserDto user = super.get(id);
        user.setPassword("");
        return user;
    }

    @Override
    public long create(UserDto userDto) {
        Optional<User> u = repo.findByUsername(userDto.getUsername());
        if (u.isPresent())
            throw new UserAlreadyExistsException(String.format("The username '%s' is already taken", userDto.getUsername()));

        //when creating a new user, encode the password
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        return super.create(userDto);
    }

    @Override
    public void update(UserDto userDto, long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Could not find user with id" + id));

        //when updating a new user, encode the password
        if (!userDto.getPassword().isEmpty())
            userDto.setPassword(encoder.encode(userDto.getPassword()));
        else
            userDto.setPassword(u.getPassword());
        super.update(userDto, id);
    }
}
