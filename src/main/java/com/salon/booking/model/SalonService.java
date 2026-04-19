package com.salon.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalonService {

    @Id
    private String id;

    private String name;
    private int duration;
    private double price;
}