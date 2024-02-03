package com.espresso.usersservice.services;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Update.*;

import com.espresso.grpc.users.User;
import com.espresso.grpc.users.UserId;
import com.espresso.grpc.users.UsersServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.mongodb.core.MongoTemplate;

@GrpcService
@RequiredArgsConstructor
public class UsersServiceImpl extends UsersServiceGrpc.UsersServiceImplBase {
  private static final Long EXPIRATION_DURATION_DAYS = 7L;

  private final MongoTemplate mongoTemplate;

  @Override
  public void createUser(User user, StreamObserver<UserId> responseObserver) {
    com.espresso.usersservice.entities.User userForSave =
        com.espresso.usersservice.entities.User.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    this.mongoTemplate.insert(userForSave);
    UserId userId = UserId.newBuilder().setUid(userForSave.getId()).build();

    responseObserver.onNext(userId);
    responseObserver.onCompleted();
  }

  @Override
  public void getUser(UserId userId, StreamObserver<User> responseObserver) {
    com.espresso.usersservice.entities.User user =
        this.mongoTemplate.findById(userId.getUid(), com.espresso.usersservice.entities.User.class);

    User.Builder userBuilder =
        User.newBuilder()
            .setUid(user.getId())
            .setUsername(user.getUsername())
            .setEmail(user.getEmail());

    responseObserver.onNext(userBuilder.build());
    responseObserver.onCompleted();
  }

  @Override
  public void removeUser(UserId userId, StreamObserver<Empty> responseObserver) {
    this.mongoTemplate.updateFirst(
        query(where("id").is(userId.getUid())),
        update("removedAt", LocalDate.now()),
        com.espresso.usersservice.entities.User.class);

    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public void setExpiration(Empty empty, StreamObserver<Empty> responseObserver) {
    this.mongoTemplate.updateMulti(
        query(where("removedAt").lt(LocalDate.now().minusDays(EXPIRATION_DURATION_DAYS))),
        update("expired", true),
        com.espresso.usersservice.entities.User.class);

    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }

  @Override
  public void removeExpiration(Empty empty, StreamObserver<Empty> responseObserver) {
    this.mongoTemplate.remove(
        query(where("expired").is(true)), com.espresso.usersservice.entities.User.class);

    responseObserver.onNext(Empty.getDefaultInstance());
    responseObserver.onCompleted();
  }
}
