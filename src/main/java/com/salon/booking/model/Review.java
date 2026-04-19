package com.salon.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.time.LocalDate;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    private String id;

    private String appointmentId;
    private String userId;
    private String userName;
    private String barberId;
    private String barberName;

    private int rating;       // 1 to 5
    private String comment;   // optional text

    private LocalDate date;
}