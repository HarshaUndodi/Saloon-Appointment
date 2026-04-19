package com.salon.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    private String id;

    private String userId;
    private List<String> serviceIds;
    private String barberId;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status;      // BOOKED, CANCELLED, DONE

    private String paymentMode; // "cash" or "upi"
}