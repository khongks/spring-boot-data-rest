package com.apress.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableGlobalAuthentication
public class InMemorySecurityConfiguration {

  @Autowired
  public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //BCryptPasswordEncoder encoder = passwordEncoder();
    auth.inMemoryAuthentication().
      withUser("user").password("password").roles("USER").
      and().
      withUser("admin").password("password").roles("USER", "ADMIN");
  }

  @SuppressWarnings("deprecation")
  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }
}