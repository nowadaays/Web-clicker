package com.nowadays.todo_app.Component;

import com.nowadays.todo_app.Entity.Upgrade;
import com.nowadays.todo_app.Repository.UpgradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UpgradeRepository upgradeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (upgradeRepository.count() == 0) {
            Upgrade upgrade = new Upgrade();
            upgrade.setName("Clicks Boost");
            upgrade.setCost(0L);
            upgrade.setClickPerHour(2L);
            upgradeRepository.save(upgrade);
        }
    }
}
