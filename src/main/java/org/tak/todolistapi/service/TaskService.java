package org.tak.todolistapi.service;

import jakarta.validation.Valid;
import org.tak.todolistapi.dto.CategoryDTO;
import org.tak.todolistapi.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, @Valid CategoryDTO categoryDTO);

    String deleteCategory(Long id);

    //Task methods
    List<TaskDTO> getTasksByCategoryId(Long id);

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    TaskDTO createTask(Long id, @Valid TaskDTO taskDTO, Long userId);

    TaskDTO updateTask(Long id, @Valid TaskDTO taskDTO);

    String deleteTask(Long id);

    TaskDTO markTaskAsDone(Long id);

}
