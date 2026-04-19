package com.salon.booking.repository;

import com.salon.booking.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByBarberId(String barberId);
    boolean existsByAppointmentId(String appointmentId); // prevent duplicate reviews
}