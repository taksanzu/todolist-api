package org.tak.todolistapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.todolistapi.dto.UserDTO;
import org.tak.todolistapi.exception.APIException;
import org.tak.todolistapi.exception.ResourceNotFoundException;
import org.tak.todolistapi.model.ERole;
import org.tak.todolistapi.model.Role;
import org.tak.todolistapi.model.User;
import org.tak.todolistapi.repository.RoleRepository;
import org.tak.todolistapi.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        List<UserDTO> userDTOs = userRepository.findAll().stream()
                .map(user -> {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    userDTO.setRoles(user.getRoles().stream()
                            .map(role -> role.getRoleName().name())
                            .collect(Collectors.toSet()));
                    return userDTO;
                })
                .toList();

        return userDTOs;
    }


    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public List<UserDTO> getAllUsersWithRoleUser(ERole role) {
        Role userRole = roleRepository.findByRoleName(role)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", role.name()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        List<UserDTO> userDTOs = userRepository.findAllByRoles(roles).stream()
                .map(user -> {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    userDTO.setRoles(user.getRoles().stream()
                            .map(r -> r.getRoleName().name())
                            .collect(Collectors.toSet()));
                    return userDTO;
                })
                .toList();
        return userDTOs;

    }

}
