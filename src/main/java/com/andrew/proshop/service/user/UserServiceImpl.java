package com.andrew.proshop.service.user;

import com.andrew.proshop.dto.InfoDto;
import com.andrew.proshop.dto.mail.MailDTO;
import com.andrew.proshop.dto.mail.RestoreMailDto;
import com.andrew.proshop.dto.user.CreateUpdateUserDto;
import com.andrew.proshop.dto.user.RestorePasswordDto;
import com.andrew.proshop.dto.user.UserDto;
import com.andrew.proshop.exception.EntityAlreadyExistsException;
import com.andrew.proshop.exception.EntityCouldNotBeFoundException;
import com.andrew.proshop.model.Role;
import com.andrew.proshop.model.User;
import com.andrew.proshop.repository.UserRepository;
import com.andrew.proshop.service.NotificationService;
import com.andrew.proshop.service.user.convertors.UserToUserDtoConvertor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder passwordEncoder;
    private UserToUserDtoConvertor userToUserDtoConvertor;
    private NotificationService notificationService;

    @Override
    public UserDto createUser(CreateUpdateUserDto newUser) {

        String login = ObjectUtils.firstNonNull(newUser.getEmail());

        if (userRepository.findByEmail(login).isPresent()) {
            throw new EntityAlreadyExistsException("User already exists");
        }

        String encodePassword = passwordEncoder.encode(newUser.getPassword());
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(encodePassword);
        user.setStatus(newUser.getStatus());
        fillRoles(newUser, user);

        user = userRepository.save(user);

        return convertToDto(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityCouldNotBeFoundException(String.format("User with id %d could not found", id)));
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public UserDto updateUser(Long id, CreateUpdateUserDto updateUser) {
        User user = getUserById(id);

        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());

        return convertToDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByUsername(login).orElseThrow(() ->
                new EntityCouldNotBeFoundException(String.format("User with username %s could not found", login)));
    }

    @Override
    public void sendRestoreRequest(RestoreMailDto restorePasswordDto) {
        User userByEmail = getUserByEmail(restorePasswordDto.getRestoreMail());
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString().toUpperCase(Locale.ROOT).substring(0, 4);
        userByEmail.setRestoreCode(code);
        userRepository.save(userByEmail);
        MailDTO restoreMail = MailDTO.builder()
                .emailTo(userByEmail.getEmail())
                .subject("Password restore")
                .message(code)
                .build();
        try {
            notificationService.sendMail(restoreMail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InfoDto restorePassword(RestorePasswordDto restorePasswordDto) {
        User userByEmail = getUserByEmail(restorePasswordDto.getEmail());
        if (!userByEmail.getRestoreCode().equals(restorePasswordDto.getCode())) {
            return new InfoDto("Incorrect code");
        }
        if (!restorePasswordDto.getNewPassword().equals(restorePasswordDto.getRepeatPassword())) {
            return new InfoDto("Passwords mismatch");
        }
        String encodePassword = passwordEncoder.encode(restorePasswordDto.getNewPassword());
        userByEmail.setRestoreCode("");
        userByEmail.setPassword(encodePassword);
        userRepository.save(userByEmail);
        return new InfoDto("Restored success");
    }

    private void fillRoles(CreateUpdateUserDto newUser, User user) {
        List<Role> roles = newUser.getRoles().stream()
                .map(roleService::getByName)
                .collect(toList());
        user.getRoles().clear();
        user.getRoles().addAll(roles);
    }

    private UserDto convertToDto(User user) {
        return userToUserDtoConvertor.convert(user);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityCouldNotBeFoundException(String.format("User with id %d could not found", id)));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new EntityCouldNotBeFoundException(String.format("User with email %s could not found", email)));
    }
}
