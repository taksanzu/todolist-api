package org.tak.todolistapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tak.todolistapi.dto.GroupDTO;
import org.tak.todolistapi.exception.APIException;
import org.tak.todolistapi.exception.ResourceNotFoundException;
import org.tak.todolistapi.model.Group;
import org.tak.todolistapi.repository.GroupRepository;
import org.tak.todolistapi.repository.UserRepository;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        if (groups.isEmpty()) {
            throw new ResourceNotFoundException("No groups found");
        }
        return groups.stream()
                .map(group -> {
                    GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
                    return groupDTO;
                })
                .toList();
    }

    @Override
    public GroupDTO createGroup(GroupDTO groupDTO, Long moderatorId) {
        if (groupRepository.existsByGroupName(groupDTO.getGroupName())) {
            throw new APIException("Group already exists");
        }
        Group group = modelMapper.map(groupDTO, Group.class);
        group.setModerator(userRepository.findById(moderatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Moderator", moderatorId)));
        group = groupRepository.save(group);
        GroupDTO createdGroupDTO = modelMapper.map(group, GroupDTO.class);
        return createdGroupDTO;
    }

    @Override
    public GroupDTO updateGroup(Long id, GroupDTO groupDTO, Long moderatorId) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", id));
        if (!group.getGroupName().equals(groupDTO.getGroupName()) && groupRepository.existsByGroupName(groupDTO.getGroupName())) {
            throw new APIException("Group already exists");
        }
        group.setGroupName(groupDTO.getGroupName());
        group.setModerator(userRepository.findById(moderatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Moderator", moderatorId)));
        group = groupRepository.save(group);
        GroupDTO updatedGroupDTO = modelMapper.map(group, GroupDTO.class);
        return updatedGroupDTO;
    }

}
