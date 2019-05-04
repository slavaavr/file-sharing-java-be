package ava.config.security;

import ava.db.entity.UserEntity;
import ava.error.NotValidEmail;
import ava.error.NotValidTokenException;
import ava.service.UserService;
import ava.util.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ava.common.Constants.AUTH_HEADER;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(AUTH_HEADER);
        Principal principal = new Principal(false, null);
        if (token != null) {
            String userEmail;
            try {
                userEmail = jwtTokenUtil.getEmailFromToken(token);
            } catch (RuntimeException e) {
                throw new NotValidTokenException();
            }
            UserEntity user = userService.getUserByEmail(userEmail);
            if (user == null) {
                throw new NotValidEmail();
            }
            principal = new Principal(true, user);
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
