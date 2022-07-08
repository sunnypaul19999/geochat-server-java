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

@SpringBootApplication
@EnableWebSecurity
public class MainApplication {

	@Autowired
	GeoSecurityUserServiceImpl geoSecurityUserServiceImpl;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors().disable()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user/register").permitAll()
				.antMatchers("/geochat/**").authenticated()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginProcessingUrl("/user/authenticate")
				.and()
				.httpBasic();

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new GeoSecurityBasicUserAuthenticationProvider(geoSecurityUserServiceImpl, passwordEncoder());
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
