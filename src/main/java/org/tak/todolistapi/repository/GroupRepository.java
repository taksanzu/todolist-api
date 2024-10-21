package org.tak.todolistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tak.todolistapi.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByGroupName(String groupName);
}
