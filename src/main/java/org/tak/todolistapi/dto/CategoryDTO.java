package org.tak.todolistapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50, message = "Category name length must be between 3 and 50 characters")
    private String name;

    private Date createdAt;

    private Date updatedAt;
}
