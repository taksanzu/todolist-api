package org.tak.todolistapi.service;

import org.tak.todolistapi.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    List<GroupDTO> getAllGroups();

    GroupDTO createGroup(GroupDTO groupDTO, Long moderatorId);

    GroupDTO updateGroup(Long id, GroupDTO groupDTO, Long moderatorId);
}
