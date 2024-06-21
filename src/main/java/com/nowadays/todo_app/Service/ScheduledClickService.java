package com.nowadays.todo_app.Service;

import com.nowadays.todo_app.Entity.Click;
import com.nowadays.todo_app.Entity.User;
import com.nowadays.todo_app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledClickService {
    @Autowired
    private ClickService clickService;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 1000)
    public void addClicksToUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Click click = clickService.getClickForUser(user.getUsername());
            if (click != null) {
                Long clickPerHouse = click.getClicksPerHouse() != null ? click.getClicksPerHouse() : 0L;
                click.setCount(click.getCount() + clickPerHouse);
                clickService.save(click);
            }
        }
    }
}
