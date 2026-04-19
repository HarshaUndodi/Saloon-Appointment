package com.salon.booking.controller;

import com.salon.booking.model.Barber;
import com.salon.booking.repository.BarberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {

    private final BarberRepository repo;

    public BarberController(BarberRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Barber> getAvailableBarbers() {
        return repo.findByAvailableTrue();
    }

    // ✅ ADMIN — Get ALL barbers (including unavailable)
    @GetMapping("/all")
    public List<Barber> getAllBarbers() {
        return repo.findAll();
    }

    @PostMapping
    public Barber addBarber(@RequestBody Barber barber) {
        return repo.save(barber);
    }

    // ✅ ADMIN — Toggle barber availability
    @PatchMapping("/{id}/toggle")
    public Barber toggleAvailability(@PathVariable String id) {
        Barber barber = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Barber not found"));
        barber.setAvailable(!barber.isAvailable());
        return repo.save(barber);
    }

    // ✅ ADMIN — Delete barber
    @DeleteMapping("/{id}")
    public Map<String, String> deleteBarber(@PathVariable String id) {
        repo.deleteById(id);
        return Map.of("status", "DELETED");
    }
}