package com.nowadays.todo_app.Service;

import com.nowadays.todo_app.Entity.Click;
import com.nowadays.todo_app.Entity.ShopItem;
import com.nowadays.todo_app.Entity.User;
import com.nowadays.todo_app.Repository.ClickRepository;
import com.nowadays.todo_app.Repository.ShopItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClickService {
    @Autowired
    private ClickRepository clickRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopItemRepository shopItemRepository;

    public Click getClickForUser(String username) {
        User user = userService.findByUsername(username);
        return clickRepository.findByUser(user).orElseGet(() -> {
            Click click = new Click();
            click.setCount(0L);
            click.setClicksPerHouse(0L);
            click.setUser(user);
            return clickRepository.save(click);
        });
    }

    public void incrementClick(String username) {
        Click click = getClickForUser(username);
        click.setCount(click.getCount() + 1);
        clickRepository.save(click);
    }

    public int getUserLevel(String username) {
        Click click = getClickForUser(username);
        long clickCount = click.getCount();
        if (clickCount >= 1000 && clickCount < 10000) {
            return 2;
        } else if (clickCount >= 10000 && clickCount < 100000) {
            return 3;
        } else if (clickCount >= 100000 && clickCount < 500000) {
            return 4;
        } else if (clickCount >= 500000 && clickCount < 1000000) {
            return 5;
        } else if (clickCount >= 1000000 && clickCount < 10000000) {
            return 6;
        } else if (clickCount >= 10000000 && clickCount < 50000000) {
            return 7;
        } else if (clickCount >= 50000000 && clickCount < 100000000) {
            return 8;
        } else if (clickCount >= 100000000 && clickCount < 1000000000) {
            return 9;
        } else if (clickCount >= 1000000000) {
            return 10;
        } else {
            return 1;
        }
    }

    public boolean purchaseItem(String username, Long itemId) {
        Click click = getClickForUser(username);
        ShopItem item = shopItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (click.getCount() >= item.getPrice()) {
            click.setCount(click.getCount() - item.getPrice());
            click.setClicksPerHouse(click.getClicksPerHouse() + item.getClicksPerHour());
            clickRepository.save(click);
            return true;
        }
        return false;
    }

    @Transactional
    public void save(Click click) {
        clickRepository.save(click);
    }
}
