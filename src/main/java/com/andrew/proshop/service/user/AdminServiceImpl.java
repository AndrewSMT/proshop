package com.andrew.proshop.service.user;

import com.andrew.proshop.model.User;
import com.andrew.proshop.model.UserStatus;
import com.andrew.proshop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void banUser(Long id) {
        User user = userService.getUser(id);
        user.setStatus(UserStatus.BANNED);
        userRepository.save(user);
    }
}
