package com.nowadays.todo_app.Repository;

import com.nowadays.todo_app.Entity.Upgrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpgradeRepository extends JpaRepository<Upgrade, Long> {
}
