package com.salon.booking.controller;

import com.salon.booking.model.Review;
import com.salon.booking.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository repo;

    public ReviewController(ReviewRepository repo) {
        this.repo = repo;
    }

    // Submit a review (called from customer screen after service done)
    @PostMapping
    public Map<String, Object> submitReview(@RequestBody Review review) {
        // Prevent duplicate review for the same appointment
        if (repo.existsByAppointmentId(review.getAppointmentId())) {
            return Map.of("status", "ALREADY_REVIEWED");
        }
        review.setDate(LocalDate.now());
        repo.save(review);
        return Map.of("status", "SAVED");
    }

    // Get all reviews (admin)
    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return repo.findAll();
    }

    // Get reviews for a specific barber
    @GetMapping("/barber/{barberId}")
    public List<Review> getByBarber(@PathVariable String barberId) {
        return repo.findByBarberId(barberId);
    }

    // Admin — delete a review
    @DeleteMapping("/{id}")
    public Map<String, String> deleteReview(@PathVariable String id) {
        repo.deleteById(id);
        return Map.of("status", "DELETED");
    }
}