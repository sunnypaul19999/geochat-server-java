package helios.server.geochat;

import helios.server.geochat.security.authentication.filter.JWTAuthFilter;
import helios.server.geochat.security.authentication.provider.GeoSecurityBasicUserAuthenticationProvider;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@SpringBootApplication
@EnableWebSecurity
public class MainApplication {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired GeoSecurityUserServiceImpl geoSecurityUserServiceImpl;

  @Autowired private AuthenticationConfiguration authenticationConfiguration;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  public void configureCors(HttpSecurity http) throws Exception {

    http.cors(
        corsConfigurer -> {
          CorsConfigurationSource configurationSource =
              httpRequest -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();

                corsConfiguration.setAllowedOrigins(
                    List.of("http://localhost:3000", "http://localhost:3001"));

                // client will be sending in cookies and the authorization header
                corsConfiguration.setAllowCredentials(true);

                // when client is sending credentials sending from server '*' will fail cors
                // we need add all required headers in Access-Control-Allow-Headers (allowedHeaders)
                // So, if we wanted to accept all headers we need type it individually, cumbersome
                // Spring makes it easy by replacing '*' with incoming headers instead of '*' so
                // need to worry, and we can accept all incoming headers even if client is sending
                // credentials
                corsConfiguration.addAllowedHeader("*");

                corsConfiguration.setAllowedMethods(
                    List.of(
                        HttpMethod.GET.toString(),
                        HttpMethod.POST.toString(),
                        HttpMethod.DELETE.toString(),
                        HttpMethod.PUT.toString()));

                return corsConfiguration;
              };

          corsConfigurer.configurationSource(configurationSource);
        });
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // csrf disabled
    http.csrf().disable();

    // configuring cors
    configureCors(http);

    // adding the authentication provider
    http.authenticationProvider(geoSecurityBasicUserAuthenticationProvider());

    // adding JWTFilter
    http.addFilterAt(
        new JWTAuthFilter(
            authenticationManager(authenticationConfiguration), geoSecurityUserServiceImpl),
        UsernamePasswordAuthenticationFilter.class);

    http.authorizeRequests()
        .antMatchers("/geopoint/**/delete/{id}")
        .hasAuthority("USER")
        .antMatchers("/user/login")
        .authenticated()
        .mvcMatchers("/user/register")
        .permitAll()
        .anyRequest()
        .authenticated();

    // setting authentication type to http
    http.httpBasic();

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {

    return authenticationConfiguration.getAuthenticationManager();
  }

  //   creating bean for basic authentication provider
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
    return new BCryptPasswordEncoder();
  }
}
