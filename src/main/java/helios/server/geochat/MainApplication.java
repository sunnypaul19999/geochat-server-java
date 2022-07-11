package helios.server.geochat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import helios.server.geochat.security.authenticationProvider.basicAuthenticationProvider.GeoSecurityBasicUserAuthenticationProvider;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SpringBootApplication
@EnableWebSecurity
public class MainApplication {

  @Autowired GeoSecurityUserServiceImpl geoSecurityUserServiceImpl;

  //  @Bean
  //  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  //    http.csrf()
  //        .disable()
  //        .cors(
  //            corsConfigurer -> {
  //              CorsConfigurationSource configurationSource =
  //                  httpRequest -> {
  //                    CorsConfiguration corsConfiguration = new CorsConfiguration();
  //                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
  //                    corsConfiguration.setAllowCredentials(true);
  //                    corsConfiguration.addAllowedHeader("");
  //                    corsConfiguration.setAllowedMethods(
  //                        List.of(HttpMethod.GET.toString(), HttpMethod.POST.toString()));
  //
  //                    return corsConfiguration;
  //                  };
  //
  //              corsConfigurer.configurationSource(configurationSource);
  //            })
  //        .authenticationProvider(
  //            geoSecurityBasicUserAuthenticationProvider()) // adding the authentication provider
  //        .authorizeRequests()
  //        .antMatchers(HttpMethod.POST, "/user/register")
  //        .permitAll()
  //        .anyRequest()
  //        .permitAll()
  //        .and()
  //        .httpBasic();
  //
  //    return http.build();
  //  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf().disable().cors().disable().authorizeRequests().anyRequest().permitAll();

    return http.build();
  }

  // creating bean for basic authentication provider
  @Bean
  public GeoSecurityBasicUserAuthenticationProvider geoSecurityBasicUserAuthenticationProvider() {
    return new GeoSecurityBasicUserAuthenticationProvider(
        geoSecurityUserServiceImpl, passwordEncoder());
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return geoSecurityUserServiceImpl;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }
}
