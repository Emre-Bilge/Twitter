package com.example.Twitter.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component


//JWT doğrulama filtresi  gelen her istekte token geçerliliği kontrol edilir.

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private  JwtService jwtService; // JWT'yi kontrol etmek için
    private  UserDetailsService userDetailsService; // Kullanıcı detaylarını almak için

@Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Header'ları loglama (isteğe bağlı, geliştirme sırasında yardımcı olabilir)
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
         //   log.debug("Header - {}: {}", headerName, request.getHeader(headerName));
        }

        // Authorization başlığındaki token'ı al
        final String authHeader = request.getHeader("Authorization");
      //  log.debug("Auth header: {}", authHeader);

        // Eğer authHeader null veya "Bearer " ile başlamıyorsa, geçerli bir token yok
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        //    log.debug("Geçerli auth header bulunamadı.");
            filterChain.doFilter(request, response); // Zinciri devam ettir
            return;
        }

        // Token'ı almak ve geçerliliğini kontrol etmek için
        try {
            final String jwt = authHeader.substring(7); // "Bearer " kısmını çıkar

            final String username = jwtService.extractUsername(jwt); // JWT içinden username'i çıkar

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Eğer SecurityContext'te kullanıcı yoksa, loadUserByUsername ile kullanıcıyı yükle
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
          //      log.debug("User detayları yüklendi: {}", userDetails.getUsername());

                // Token geçerli mi, kontrol et
                if (jwtService.isTokenValid(jwt, userDetails)) {
             //       log.debug("Token bu kullanıcı için geçerli: {}", userDetails.getUsername());
                    // Token geçerliyse, kullanıcıyı SecurityContext'e ekle
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
          //          log.debug("Token geçerli değil: {}", userDetails.getUsername());
                }
            }
        } catch (Exception e) {
       //     log.error("JWT token error: ", e);
        }

        // İstek zincirini devam ettir
        filterChain.doFilter(request, response);
    }
}