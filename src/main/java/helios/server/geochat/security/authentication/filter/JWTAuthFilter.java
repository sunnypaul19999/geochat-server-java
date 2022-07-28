package helios.server.geochat.security.authentication.filter;

import helios.server.geochat.dto.request.GeoUserDTO;
import helios.server.geochat.exceptions.serviceExceptions.geoUserServiceException.GeoUserNotFoundException;
import helios.server.geochat.model.GeoUser;
import helios.server.geochat.service.impl.GeoSecurityUserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  private final GeoSecurityUserServiceImpl geoUserService;

  private Cookie jwtTokenCookie;

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public JWTAuthFilter(
      AuthenticationManager authenticationManager, GeoSecurityUserServiceImpl geoUserService) {
    this.authenticationManager = authenticationManager;
    this.geoUserService = geoUserService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwtToken = jwtTokenCookie.getValue();

    JwtParser jwtParser =
        Jwts.parserBuilder().setSigningKey("TjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8".getBytes()).build();

    Claims jwtBody = jwtParser.parseClaimsJws(jwtToken).getBody();

    logger.debug(String.format("--- Username : %s ", jwtBody));

    if (jwtParser.isSigned(jwtToken)) {
      final GeoUserDTO geoUserDTO = new GeoUserDTO((String) jwtBody.get("username"));
      try {
        final GeoUser geoUser = geoUserService.getUser(geoUserDTO);

        if (geoUser.getJwtToken().equals(jwtToken)) {

          response.setStatus(200);

          return;
        }
      } catch (GeoUserNotFoundException e) {

        response.setStatus(400);
      }

      response.setStatus(403);
    }

    response.setStatus(403);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    if (request.getCookies() == null) return true;

    List<Cookie> cookies =
        Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equalsIgnoreCase("GEOCHATJWTTOKEN"))
            .toList();

    if (cookies.isEmpty()) {

      return true;

    } else {

      jwtTokenCookie = cookies.get(0);

      return false;
    }
  }
}
