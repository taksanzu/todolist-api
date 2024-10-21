package org.tak.todolistapi.service;

import org.tak.todolistapi.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    List<UserDTO> getAllModerators();

    UserDTO getUserById(Long id);

    UserDTO updateUsers(Long id, UserDTO userDTO, Long groupId);

}
