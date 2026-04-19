package com.salon.booking.repository;

import com.salon.booking.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    boolean existsByDateAndStartTimeAndBarberId(
            LocalDate date,
            LocalTime startTime,
            String barberId
    );
    List<Appointment> findByDateAndBarberId(LocalDate date, String barberId);
}