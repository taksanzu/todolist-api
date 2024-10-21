package org.tak.todolistapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.todolistapi.dto.UserDTO;
import org.tak.todolistapi.exception.APIException;
import org.tak.todolistapi.exception.ResourceNotFoundException;
import org.tak.todolistapi.model.ERole;
import org.tak.todolistapi.model.Group;
import org.tak.todolistapi.model.Role;
import org.tak.todolistapi.model.User;
import org.tak.todolistapi.repository.GroupRepository;
import org.tak.todolistapi.repository.RoleRepository;
import org.tak.todolistapi.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        List<UserDTO> userDTOs = userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
        return userDTOs;
    }

    @Override
    public List<UserDTO> getAllModerators() {
        if (userRepository.findAllModerators().isEmpty()) {
            throw new ResourceNotFoundException("No moderators found");
        }
        List<UserDTO> userDTOs = userRepository.findAllModerators().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
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
    public UserDTO updateUsers(Long id, UserDTO userDTO, Long groupId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId));

        user.setGroup(group);

        Set<String> strRoles = userDTO.getRoles();

        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new APIException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

}
