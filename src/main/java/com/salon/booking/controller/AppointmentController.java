package com.salon.booking.controller;

import com.salon.booking.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // Book appointment
    @PostMapping
    public Map<String, Object> bookAppointment(
            @RequestParam String userId,
            @RequestParam List<String> serviceIds,
            @RequestParam String barberId,
            @RequestParam String date,
            @RequestParam String time
    ) {
        return service.bookAppointment(userId, serviceIds, barberId,
                LocalDate.parse(date), LocalTime.parse(time));
    }

    // Get available slots
    @GetMapping("/slots")
    public List<String> getSlots(
            @RequestParam List<String> serviceIds,
            @RequestParam String barberId,
            @RequestParam String date
    ) {
        return service.getAvailableSlots(serviceIds, barberId, LocalDate.parse(date));
    }

    // ADMIN — Get all appointments
    @GetMapping("/all")
    public List<?> getAllAppointments() {
        return service.getAllAppointments();
    }

    // ADMIN — Cancel appointment
    @PatchMapping("/{id}/cancel")
    public Map<String, Object> cancelAppointment(@PathVariable String id) {
        return service.cancelAppointment(id);
    }

    // ADMIN — Hard delete appointment
    @DeleteMapping("/{id}")
    public Map<String, String> deleteAppointment(@PathVariable String id) {
        service.deleteAppointment(id);
        return Map.of("status", "DELETED");
    }

    // ADMIN — Mark appointment as DONE (barber finished service)
    // Earnings tracker counts DONE appointments, not BOOKED
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable String id) {
        Map<String, Object> result = service.completeAppointment(id);
        if (result.containsKey("error")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    // Get single appointment by ID (used by customer polling for DONE status)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable String id) {
        return service.getAllAppointments().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}