package com.apress.spring.config;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableGlobalAuthentication
public class JdbcSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
    RowMapper<User> userRowMapper = (ResultSet rs, int i) -> 
      new User(
        rs.getString("ACCOUNT_NAME"), //username
        rs.getString("PASSWORD"), //password
        rs.getBoolean("ENABLED"), //enabled
        rs.getBoolean("ENABLED"), // accountNonExpired
        rs.getBoolean("ENABLED"), //credentialsNonExpired
        rs.getBoolean("ENABLED"), //accountNonLocked
        AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
      return username -> 
        jdbcTemplate.queryForObject("select * from ACCOUNT where ACCOUNT_NAME=?", userRowMapper, username);
  }

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.userDetailsService);
  }

  @SuppressWarnings("deprecation")
  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }
}