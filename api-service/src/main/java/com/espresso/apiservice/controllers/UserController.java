package com.espresso.apiservice.controllers;

import com.espresso.apiservice.dtos.UserDTO;
import com.espresso.apiservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public String createUser(@RequestBody UserDTO user) {
    return this.userService.createUser(user);
  }

  @GetMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO getUser(@PathVariable("userId") String id) {
    return this.userService.getUser(id);
  }

  @PutMapping("/remove-user/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public void removeUser(@PathVariable("userId") String id) {
    this.userService.removeUser(id);
  }

  @PutMapping("/set-expiration")
  @ResponseStatus(HttpStatus.OK)
  public void setExpiration() {
    this.userService.setExpiration();
  }

  @DeleteMapping("/remove-expiration")
  @ResponseStatus(HttpStatus.OK)
  public void removeExpiration() {
    this.userService.removeExpiration();
  }
}
