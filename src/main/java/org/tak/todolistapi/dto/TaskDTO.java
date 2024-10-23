package org.tak.todolistapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.tak.todolistapi.model.Category;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50, message = "Title length must be between 3 and 50 characters")
    private String title;

    @NotBlank
    @Size(min = 3, max = 255, message = "Description length must be between 3 and 255 characters")
    private String description;

    private Date createdAt;

    private Date updatedAt;

    @NotNull
    @Min(value = 1, message = "Important value must be between 1 and 3")
    @Max(value = 3, message = "Important value must be between 1 and 3")
    private Integer important;

    private Date dueDate;

    private UserDTO assignee;

    private boolean done;

    @ToString.Exclude
    private Category category;

}
