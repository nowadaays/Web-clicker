package com.nowadays.todo_app.Controller;

import com.nowadays.todo_app.Entity.Click;
import com.nowadays.todo_app.Entity.User;
import com.nowadays.todo_app.Repository.ShopItemRepository;
import com.nowadays.todo_app.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.nowadays.todo_app.Service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ClickController {
    @Autowired
    private ClickService clickService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopItemRepository shopItemRepository;

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("click", clickService.getClickForUser(username));
        model.addAttribute("username", username);
        model.addAttribute("level" , clickService.getUserLevel(username));
        model.addAttribute("clicksPerHour", clickService.getClickForUser(username).getClicksPerHouse());
        return "index";
    }

    @PostMapping("/click")
    public String click(Principal principal) {
        String username = principal.getName();
        clickService.incrementClick(username);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setRoles("ROLE_USER");
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/shop")
    public String showShop(Principal principal, Model model){
        model.addAttribute("items", shopItemRepository.findAll());
        return "shop";
    }

    @PostMapping("/purchase")
    public String purchaseItem(Principal principal , @RequestParam Long itemId){
        String username = principal.getName();
        clickService.purchaseItem(username, itemId);
        return "redirect:/shop";
    }
}

@RestController
class ClickRestController {

    @Autowired
    private ClickService clickService;

    @GetMapping("/clicks")
    public ResponseEntity<Long> getClicks(Principal principal) {
        String username = principal.getName();
        Click click = clickService.getClickForUser(username);
        if (click != null) {
            return ResponseEntity.ok(click.getCount());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0L);
        }
    }
}

