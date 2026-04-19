package com.salon.booking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;

@Document(collection = "barbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Barber {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String specialty;  // "Hair", "Beard", "All"
    private boolean available;
    private String upiId;      // UPI ID for payments e.g. ravi@okaxis
}