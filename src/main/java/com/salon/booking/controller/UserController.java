package com.salon.booking.controller;

import com.salon.booking.model.User;
import com.salon.booking.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public User register(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam String phone) {
        Map<String, Object> response = new HashMap<>();
        repo.findByPhone(phone).ifPresentOrElse(
                user -> {
                    response.put("status", "EXISTS");
                    response.put("user", user);
                },
                () -> response.put("status", "NOT_FOUND")
        );
        return response;
    }

    // ✅ ADMIN — Get all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}