package com.nhlstenden.student.vigmo.services;

import com.nhlstenden.student.vigmo.dto.UserDto;
import com.nhlstenden.student.vigmo.models.User;
import com.nhlstenden.student.vigmo.repositories.UserRepository;
import com.nhlstenden.student.vigmo.transformers.MappingUtility;

public class UserService extends AbstractVigmoService<UserRepository, UserDto, User> {
    public UserService(UserRepository repo, MappingUtility mapper) {
        super(repo, mapper, UserDto.class, User.class);
    }
}
