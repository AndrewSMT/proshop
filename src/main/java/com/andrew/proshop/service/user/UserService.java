package com.andrew.proshop.service.user;

import com.andrew.proshop.dto.InfoDto;
import com.andrew.proshop.dto.mail.RestoreMailDto;
import com.andrew.proshop.dto.user.CreateUpdateUserDto;
import com.andrew.proshop.dto.user.RestorePasswordDto;
import com.andrew.proshop.dto.user.UserDto;
import com.andrew.proshop.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(CreateUpdateUserDto newUser);

    User getUser(Long id);

    List<User> getUserList();

    UserDto updateUser(Long id, CreateUpdateUserDto updateUser);

    void deleteUser(Long id);

    User findByLogin(String login);

    void sendRestoreRequest(RestoreMailDto restorePasswordDto);

    InfoDto restorePassword(RestorePasswordDto restorePasswordDto);


}
