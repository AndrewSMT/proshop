package com.andrew.proshop.service.user.convertors;

import com.andrew.proshop.converter.AbstractConverter;
import com.andrew.proshop.dto.user.UserDto;
import com.andrew.proshop.model.Role;
import com.andrew.proshop.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserToUserDtoConvertor extends AbstractConverter<User, UserDto> {

    @Override
    protected UserDto generateTarget() {
        return new UserDto();
    }

    @Override
    public void convert(User source, UserDto target) {
        target.setUsername(source.getUsername());
        target.setEmail(source.getEmail());
        List<String> roles = source.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        target.setAuthorities(roles);
    }
}

