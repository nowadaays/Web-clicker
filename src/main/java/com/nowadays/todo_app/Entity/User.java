package com.nowadays.todo_app.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;

    private String password;

    private String roles;

    @OneToMany(mappedBy = "user")
    private Set<Click> clicks;
}
