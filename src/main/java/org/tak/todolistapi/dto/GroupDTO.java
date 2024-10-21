package org.tak.todolistapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.tak.todolistapi.model.Category;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;

    private String groupName;

    private UserDTO moderator;

    private List<UserDTO> users;

    @ToString.Exclude
    private List<CategoryDTO> categories;

}
