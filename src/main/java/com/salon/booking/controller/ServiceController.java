package com.salon.booking.controller;

import com.salon.booking.model.SalonService;
import com.salon.booking.repository.ServiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceRepository repo;

    public ServiceController(ServiceRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<SalonService> getAllServices() {
        return repo.findAll();
    }

    @PostMapping
    public SalonService addService(@RequestBody SalonService service) {
        return repo.save(service);
    }

    // ✅ ADMIN — Delete service
    @DeleteMapping("/{id}")
    public Map<String, String> deleteService(@PathVariable String id) {
        repo.deleteById(id);
        return Map.of("status", "DELETED");
    }
}