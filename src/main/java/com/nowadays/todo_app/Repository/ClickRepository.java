package com.nowadays.todo_app.Repository;

import com.nowadays.todo_app.Entity.Click;
import com.nowadays.todo_app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClickRepository extends JpaRepository<Click , Long> {
    Optional<Click> findByUser(User user);
}
