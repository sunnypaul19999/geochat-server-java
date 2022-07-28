package helios.server.geochat.security.authentication.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JWTAuthFilter extends OncePerRequestFilter {

    private  AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public JWTAuthFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService){

        this.authenticationManager = authenticationManager;

        this.userDetailsService = userDetailsService;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

      Optional<String> authHeader =  Optional.of(request.getHeader("Authorization"));

        if(authHeader.isPresent()){

           final boolean startsWithBearer =   authHeader.get().trim().startsWith("Bearer");

           if(startsWithBearer){

               logger.trace(String.format("Authorization header found %s", authHeader.get().trim()));

           }

        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }

}
