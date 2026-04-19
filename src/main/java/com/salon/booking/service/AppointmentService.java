package com.salon.booking.service;

import com.salon.booking.model.Appointment;
import com.salon.booking.model.Barber;
import com.salon.booking.model.SalonService;
import com.salon.booking.repository.AppointmentRepository;
import com.salon.booking.repository.BarberRepository;
import com.salon.booking.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final ServiceRepository serviceRepo;
    private final BarberRepository barberRepo;

    public AppointmentService(AppointmentRepository appointmentRepo,
                              ServiceRepository serviceRepo,
                              BarberRepository barberRepo) {
        this.appointmentRepo = appointmentRepo;
        this.serviceRepo = serviceRepo;
        this.barberRepo = barberRepo;
    }

    public Map<String, Object> bookAppointment(String userId, List<String> serviceIds,
                                               String preferredBarber,
                                               LocalDate date, LocalTime startTime) {
        int totalDuration = 0;
        for (String id : serviceIds) {
            SalonService s = serviceRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Service not found: " + id));
            totalDuration += s.getDuration();
        }
        LocalTime endTime = startTime.plusMinutes(totalDuration);

        List<String> barbers = barberRepo.findByAvailableTrue()
                .stream().map(Barber::getId).collect(Collectors.toList());

        // 1. Try preferred barber
        if (isAvailable(preferredBarber, date, startTime, endTime)) {
            Appointment saved = saveAppointment(userId, serviceIds, preferredBarber, date, startTime, endTime);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "BOOKED");
            response.put("id", saved.getId());     // ← ID returned so frontend can poll for DONE status
            response.put("barberId", preferredBarber);
            response.put("startTime", startTime.toString());
            response.put("endTime", endTime.toString());
            return response;
        }

        // 2. Try other barbers
        for (String barber : barbers) {
            if (!barber.equals(preferredBarber) && isAvailable(barber, date, startTime, endTime)) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "BUSY");
                response.put("message", "Preferred barber not available");
                response.put("alternativeBarber", barber);
                return response;
            }
        }

        // 3. All busy
        Map<String, Object> response = new HashMap<>();
        response.put("status", "FULL");
        response.put("message", "No barbers available at this time");
        return response;
    }

    private boolean isAvailable(String barberId, LocalDate date,
                                LocalTime startTime, LocalTime endTime) {
        List<Appointment> existing = appointmentRepo.findByDateAndBarberId(date, barberId);
        for (Appointment appt : existing) {
            if ("CANCELLED".equals(appt.getStatus())) continue;
            boolean overlap = startTime.isBefore(appt.getEndTime())
                    && appt.getStartTime().isBefore(endTime);
            if (overlap) return false;
        }
        return true;
    }

    private Appointment saveAppointment(String userId, List<String> serviceIds,
                                        String barberId, LocalDate date,
                                        LocalTime startTime, LocalTime endTime) {
        Appointment appt = new Appointment();
        appt.setUserId(userId);
        appt.setServiceIds(serviceIds);
        appt.setBarberId(barberId);
        appt.setDate(date);
        appt.setStartTime(startTime);
        appt.setEndTime(endTime);
        appt.setStatus("BOOKED");
        return appointmentRepo.save(appt);
    }

    public List<String> getAvailableSlots(List<String> serviceIds,
                                          String barberId, LocalDate date) {
        int totalDuration = 0;
        for (String id : serviceIds) {
            SalonService s = serviceRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Service not found: " + id));
            totalDuration += s.getDuration();
        }
        int duration = totalDuration;
        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(21, 0);
        while (!start.plusMinutes(duration).isAfter(end)) {
            allSlots.add(start);
            start = start.plusMinutes(30);
        }
        List<Appointment> booked = appointmentRepo.findByDateAndBarberId(date, barberId);
        List<String> available = new ArrayList<>();
        for (LocalTime slotStart : allSlots) {
            LocalTime slotEnd = slotStart.plusMinutes(duration);
            boolean isFree = true;
            for (Appointment appt : booked) {
                if ("CANCELLED".equals(appt.getStatus())) continue;
                boolean overlap = slotStart.isBefore(appt.getEndTime())
                        && appt.getStartTime().isBefore(slotEnd);
                if (overlap) { isFree = false; break; }
            }
            if (isFree) available.add(slotStart.toString());
        }
        return available;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Map<String, Object> cancelAppointment(String id) {
        Appointment appt = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
        appt.setStatus("CANCELLED");
        appointmentRepo.save(appt);
        return Map.of("status", "CANCELLED", "id", id);
    }

    public void deleteAppointment(String id) {
        appointmentRepo.deleteById(id);
    }

    // ADMIN — Mark service as DONE (earnings update happens here, not at booking)
    public Map<String, Object> completeAppointment(String id) {
        return appointmentRepo.findById(id).map(appt -> {
            appt.setStatus("DONE");
            appointmentRepo.save(appt);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "DONE");
            response.put("id", id);
            return response;
        }).orElseGet(() -> {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "NOT_FOUND");
            return err;
        });
    }
}