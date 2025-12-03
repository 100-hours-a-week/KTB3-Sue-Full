package com.example.spring_restapi.security;

import com.example.spring_restapi.dto.auth.CustomUserDetails;
import com.example.spring_restapi.model.User;
import com.example.spring_restapi.model.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 요청에 대해서 한 번만 동작
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();

        System.out.println("=== [JWTFilter] START ===");
        System.out.println("[JWTFilter] method = " + method + ", uri = " + uri);

        // request에서 Authorization header 가져오기
        String authorization = request.getHeader("Authorization");
        System.out.println("[JWTFilter] Authorization header = " + authorization);

        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1];

        if(jwtUtil.isExpired(token)){
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String username = jwtUtil.getUsername(token);
        String rawRole = jwtUtil.getRole(token); // "[ROLE_USER]" 또는 "ROLE_USER" 라고 가정
        System.out.println("rawRole from token = " + rawRole);


        String cleaned = rawRole.replace("[", "").replace("]", "");
        if (cleaned.startsWith("ROLE_")) {
            cleaned = cleaned.substring(5);
        }
        UserRole role = UserRole.valueOf(cleaned);

        User user = new User();
        user.setEmail(username);
        user.setPassword("TempPassword");
        user.setUserRole(role);  // 이제 USER / ADMIN으로 매핑됨

        CustomUserDetails userDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
