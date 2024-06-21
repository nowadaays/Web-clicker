package com.nowadays.todo_app.Repository;

import com.nowadays.todo_app.Entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem , Long> {
}
