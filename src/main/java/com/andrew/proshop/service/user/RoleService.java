package com.andrew.proshop.service.user;

import com.andrew.proshop.model.Role;

public interface RoleService {

    Role getByName(Role.RoleEnum role);
}
