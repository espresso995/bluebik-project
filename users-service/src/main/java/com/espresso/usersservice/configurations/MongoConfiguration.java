package com.espresso.usersservice.configurations;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@PropertySource("classpath:application.yml")
@RequiredArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {
  private final Environment environment;

  @Override
  protected String getDatabaseName() {
    return this.environment.getProperty("mongo.database");
  }

  @Override
  protected void configureClientSettings(MongoClientSettings.Builder builder) {
    final String username = this.environment.getProperty("mongo.username");
    final String password = this.environment.getProperty("mongo.password");
    final String authenticationDatabase =
        this.environment.getProperty("mongo.authentication.database");
    final String host = this.environment.getProperty("mongo.host");
    final Integer port = this.environment.getProperty("mongo.port", Integer.class);
    builder.credential(
        MongoCredential.createCredential(username, authenticationDatabase, password.toCharArray()));
    builder.applyToClusterSettings(
        settings -> settings.hosts(Collections.singletonList(new ServerAddress(host, port))));
  }
}
