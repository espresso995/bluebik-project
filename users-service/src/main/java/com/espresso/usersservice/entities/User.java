package com.espresso.usersservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private LocalDate removedAt;
    private boolean expired;
}
