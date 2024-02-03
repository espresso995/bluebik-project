package com.espresso.apiservice.services;

import com.espresso.apiservice.dtos.UserDTO;
import com.espresso.grpc.users.User;
import com.espresso.grpc.users.UserId;
import com.espresso.grpc.users.UsersServiceGrpc;
import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UsersServiceGrpc.UsersServiceBlockingStub stub;

  public String createUser(UserDTO userDTO) {
    User.Builder userBuilder =
        User.newBuilder().setUsername(userDTO.getUsername()).setEmail(userDTO.getEmail());
    UserId userId = this.stub.createUser(userBuilder.build());
    return userId.getUid();
  }

  public UserDTO getUser(String id) {
    UserId.Builder userIdBuilder = UserId.newBuilder().setUid(id);
    User user = this.stub.getUser(userIdBuilder.build());

    return UserDTO.builder()
        .id(user.getUid())
        .username(user.getUsername())
        .email(user.getEmail())
        .build();
  }

  public void removeUser(String id) {
    UserId.Builder userIdBuilder = UserId.newBuilder().setUid(id);
    this.stub.removeUser(userIdBuilder.build());
  }

  public void setExpiration() {
    this.stub.setExpiration(Empty.getDefaultInstance());
  }

  public void removeExpiration() {
    this.stub.removeExpiration(Empty.getDefaultInstance());
  }
}
