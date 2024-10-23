package org.tak.todolistapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.todolistapi.dto.CategoryDTO;
import org.tak.todolistapi.dto.TaskDTO;
import org.tak.todolistapi.exception.APIException;
import org.tak.todolistapi.exception.ResourceNotFoundException;
import org.tak.todolistapi.model.Category;
import org.tak.todolistapi.model.Task;
import org.tak.todolistapi.repository.CategoryRepository;
import org.tak.todolistapi.repository.TaskRepository;
import org.tak.todolistapi.repository.UserRepository;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Category methods

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByIsDeletedFalse();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }
        return categories.stream()
                .map(category -> {
                    CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
                    return categoryDTO;
                })
                .toList();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new APIException("Category already exists");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(category, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        if (!category.getName().equals(categoryDTO.getName()) && categoryRepository.existsByName(categoryDTO.getName())) {
            throw new APIException("Category already exists");
        }
        category.setName(categoryDTO.getName());
        category = categoryRepository.save(category);
        CategoryDTO updatedCategoryDTO = modelMapper.map(category, CategoryDTO.class);
        return updatedCategoryDTO;
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        category.setDeleted(true);
        categoryRepository.save(category);
        return "Category with id : " + id + " deleted successfully";
    }

    //Task methods

    @Override
    public List<TaskDTO> getTasksByCategoryId(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        List<TaskDTO> taskDTOs = category.getTasks().stream()
                .map(task -> {
                    TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
                    taskDTO.setCategory(task.getCategory());
                    return taskDTO;
                })
                .toList();
        return taskDTOs;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAllByDeletedFalse();
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("No tasks found");
        }
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(task -> {
                    TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
                    taskDTO.setCategory(task.getCategory());
                    return taskDTO;
                })
                .toList();
        return taskDTOs;
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);

        taskDTO.setCategory(task.getCategory());
        return taskDTO;
    }

    @Override
    public TaskDTO createTask(Long id, TaskDTO taskDTO, Long userId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setCategory(category);
        task.setAssignee(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId)));
        task = taskRepository.save(task);
        TaskDTO savedTaskDTO = modelMapper.map(task, TaskDTO.class);
        savedTaskDTO.setCategory(task.getCategory());
        return savedTaskDTO;
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO, Long categoryId, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setImportant(taskDTO.getImportant());
        task.setDueDate(taskDTO.getDueDate());
        task.setCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId)));
        task.setAssignee(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId)));
        task = taskRepository.save(task);

        TaskDTO updatedTaskDTO = modelMapper.map(task, TaskDTO.class);
        updatedTaskDTO.setCategory(task.getCategory());
        return updatedTaskDTO;
    }

    @Override
    public String deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        task.setDeleted(true);
        taskRepository.save(task);
        return "Task with id : " + id + " deleted successfully";
    }

    @Override
    public TaskDTO markTaskAsDone(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        task.setDone(task.isDone()? false : true);
        task = taskRepository.save(task);

        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        taskDTO.setCategory(task.getCategory());
        return taskDTO;
    }
}
