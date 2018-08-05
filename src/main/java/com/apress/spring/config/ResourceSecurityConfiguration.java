package com.apress.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalAuthentication
public class ResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/api/**").authenticated()
      .and()
      .formLogin().loginPage("/login").permitAll()              
      .and()              
      .logout().permitAll(); 

      //.formLogin(); // Go to http://localhost:8080/api/
      //.httpBasic(); // curl -i -u springboot:passw0rd http://localhost:8080/api/
  }
}