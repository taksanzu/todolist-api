package org.tak.todolistapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tak.todolistapi.dto.TaskDTO;
import org.tak.todolistapi.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @GetMapping("/tasks")
//    public ResponseEntity<List<TaskDTO>> getAllTasksByAssignee(@RequestParam(required = false) Long categoryId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (categoryId != null) {
//            return new ResponseEntity<>(taskService.getAllTasksByAssigneeAndCategory(authentication.getName(), categoryId), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(taskService.getAllTasksByAssignee(authentication.getName()), HttpStatus.OK);
//    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@RequestParam Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}/done")
    public ResponseEntity<TaskDTO> markTaskAsDone(@RequestParam Long id) {
        return new ResponseEntity<>(taskService.markTaskAsDone(id), HttpStatus.OK);
    }
}
