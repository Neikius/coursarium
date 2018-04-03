package org.neikius.test.coursarium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] REST_CONTEXT = {"/v1/**"};

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // specify secured and unsecured parts of the app
    http
        .authorizeRequests()
        .antMatchers(REST_CONTEXT).authenticated()
        .anyRequest().permitAll()
        .and().httpBasic()
        .and().csrf().disable();
  }

  @Autowired
  private DataSource dataSource;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // define a simple JDBC auth, not safe, not secure
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select name as username, token as password, 1 as enabled from student where UPPER(name)=UPPER(?)")
        .authoritiesByUsernameQuery("select name as username, 'USER' as authority from student where UPPER(name)=UPPER(?) ")
        .passwordEncoder(NoOpPasswordEncoder.getInstance());
  }
}