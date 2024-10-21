package org.tak.todolistapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tak.todolistapi.dto.GroupDTO;
import org.tak.todolistapi.dto.UserDTO;
import org.tak.todolistapi.service.GroupService;
import org.tak.todolistapi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    // Group APIs
    @GetMapping("/groups")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @PostMapping("/groups/moderator/{moderatorId}")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO, @PathVariable Long moderatorId) {
        return new ResponseEntity<>(groupService.createGroup(groupDTO, moderatorId), HttpStatus.CREATED);
    }

    @PutMapping("/groups/{id}/moderator/{moderatorId}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO, @PathVariable Long moderatorId) {
        return new ResponseEntity<>(groupService.updateGroup(id, groupDTO, moderatorId), HttpStatus.OK);
    }

    // User APIs
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users/moderator")
    public ResponseEntity<List<UserDTO>> getAllModerators() {
        return new ResponseEntity<>(userService.getAllModerators(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/groups/{groupId}")
    public ResponseEntity<UserDTO> updateUsers(@PathVariable Long id, @RequestBody UserDTO userDTO, @PathVariable Long groupId) {
        return new ResponseEntity<>(userService.updateUsers(id, userDTO, groupId), HttpStatus.OK);
    }
}
