package com.salon.booking.repository;

import com.salon.booking.model.Barber;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BarberRepository extends MongoRepository<Barber, String> {
    List<Barber> findByAvailableTrue(); // ✅ only available barbers
}
