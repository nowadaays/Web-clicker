package com.nowadays.todo_app.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "click")
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long count;
    private Long clicksPerHouse = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
