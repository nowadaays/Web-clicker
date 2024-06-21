package com.nowadays.todo_app.Controller;

import com.nowadays.todo_app.Entity.Click;
import com.nowadays.todo_app.Service.ClickService;
import com.nowadays.todo_app.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ClickService clickService;

    @GetMapping("/shop-upgrade")
    public String shop(Principal principal, Model model) {
        String username = principal.getName();
        Click click = clickService.getClickForUser(username);
        model.addAttribute("click", click);
        model.addAttribute("upgrades", shopService.getAllUpgrades());
        return "shop";
    }

    @PostMapping("/purchase-upgrade")
    public String purchaseUpgrade(Principal principal, @RequestParam Long upgradeId, Model model) {
        String username = principal.getName();
        boolean success = shopService.purchaseUpgrade(username, upgradeId);

        if (success) {
            return "redirect:/";
        } else {
            model.addAttribute("error", "Not enough clicks to purchase this upgrade.");
            model.addAttribute("click", clickService.getClickForUser(username));
            model.addAttribute("upgrades", shopService.getAllUpgrades());
            return "shop";
        }
    }
}
