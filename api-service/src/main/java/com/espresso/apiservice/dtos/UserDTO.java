package com.espresso.apiservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private String id;
  private String username;
  private String email;
  @JsonIgnore private LocalDate removedAt;
  @JsonIgnore private boolean expired;
}
