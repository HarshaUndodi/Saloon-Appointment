package com.salon.booking.repository;

import com.salon.booking.model.SalonService;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<SalonService, String> {
}