package com.nowadays.todo_app.Service;

import com.nowadays.todo_app.Entity.Click;
import com.nowadays.todo_app.Entity.Upgrade;
import com.nowadays.todo_app.Repository.UpgradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ClickService clickService;

    @Autowired
    private UpgradeRepository upgradeRepository;

    public List<Upgrade> getAllUpgrades() {
        return upgradeRepository.findAll();
    }

    public boolean purchaseUpgrade(String username, Long upgradeId) {
        Click click = clickService.getClickForUser(username);
        if (click == null) {
            throw new RuntimeException("User does not have clicks data");
        }

        Upgrade upgrade = upgradeRepository.findById(upgradeId)
                .orElseThrow(() -> new RuntimeException("Upgrade not found"));

        if (click.getCount() >= upgrade.getCost()) {
            click.setCount(click.getCount() - upgrade.getCost());

            Long currentClicksPerHouse = click.getClicksPerHouse() != null ? click.getClicksPerHouse() : 0L;
            click.setClicksPerHouse(currentClicksPerHouse + upgrade.getClickPerHour());

            clickService.save(click);
            return true;
        }
        return false;
    }
}
