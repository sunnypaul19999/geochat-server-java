package helios.server.geochat.security.authentication.filter;

import helios.server.geochat.security.authentication.JWTAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  private Optional<String> authHeader;

  private Logger logger = LoggerFactory.getLogger(getClass());

  public JWTAuthFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authorizationHeaderValue = authHeader.get().trim();

    if (authorizationHeaderValue.startsWith("Bearer")) {
      
      response.setStatus(300);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    authHeader = Optional.ofNullable(request.getHeader("Authorization"));

    if (authHeader.isPresent()) {

      if (authHeader.get().trim().startsWith("Bearer")) {

        return false;
      }
    }

    return true;
  }
}
