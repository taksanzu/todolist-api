package org.tak.todolistapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @OneToOne
    @JoinColumn(name = "moderator_id", referencedColumnName = "id")
    private User moderator;

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<User> users;

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Category> categories;
}
