package org.tak.todolistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tak.todolistapi.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
