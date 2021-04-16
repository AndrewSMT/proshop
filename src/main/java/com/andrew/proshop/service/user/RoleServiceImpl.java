package com.andrew.proshop.service.user;


import com.andrew.proshop.model.Role;
import com.andrew.proshop.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Role getByName(Role.RoleEnum role) {
        return roleRepository.findByName(role.toString());
    }
}
