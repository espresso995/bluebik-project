package com.espresso.apiservice.configurations;

import com.espresso.grpc.users.UsersServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfiguration {

  @Bean
  UsersServiceGrpc.UsersServiceBlockingStub usersServiceStub() {
    Channel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
    return UsersServiceGrpc.newBlockingStub(channel);
  }
}
