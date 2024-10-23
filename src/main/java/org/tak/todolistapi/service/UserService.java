package org.tak.todolistapi.service;

import org.tak.todolistapi.dto.UserDTO;
import org.tak.todolistapi.model.ERole;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsersWithRoleUser(ERole role);
}
