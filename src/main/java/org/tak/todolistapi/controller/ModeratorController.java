package org.tak.todolistapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tak.todolistapi.dto.CategoryDTO;
import org.tak.todolistapi.dto.TaskDTO;
import org.tak.todolistapi.service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/moderator")
public class ModeratorController {
    @Autowired
    private TaskService taskService;

    //Category methods
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(taskService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getCategoryById(id), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(taskService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(taskService.updateCategory(id, categoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
        String category = taskService.deleteCategory(id);
        Map<String, String> response = Map.of("message", category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Task methods
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return new ResponseEntity<>(taskService.getTasksByCategoryId(categoryId), HttpStatus.OK);
        }
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PostMapping("/tasks/categories/{id}/assign/{userId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO, @PathVariable Long userId) {
        return new ResponseEntity<>(taskService.createTask(id, taskDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.updateTask(id, taskDTO), HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}/done")
    public ResponseEntity<TaskDTO> markTaskAsDone(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.markTaskAsDone(id), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String task = taskService.deleteTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
