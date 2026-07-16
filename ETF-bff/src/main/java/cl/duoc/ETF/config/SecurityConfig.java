package cl.duoc.ETF.config;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(authorize ->
                authorize

                    .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/api/bff/estado"
                    )
                    .permitAll()

                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/bff/mensajes"
                    )
                    .hasAnyRole(
                        "ESTUDIANTE",
                        "INSTRUCTOR",
                        "ADMIN"
                    )

                    .anyRequest()
                    .authenticated()
            )

            .oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(
                        jwtAuthenticationConverter()
                    )
                )
            );

        return http.build();
    }

    @Bean
    JwtAuthenticationConverter
    jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter
                scopesConverter =
                new JwtGrantedAuthoritiesConverter();

        Converter<
                Jwt,
                Collection<GrantedAuthority>
        > authoritiesConverter = jwt -> {

            Set<GrantedAuthority> authorities =
                    new HashSet<>();

            Collection<GrantedAuthority>
                    scopeAuthorities =
                    scopesConverter.convert(jwt);

            if (scopeAuthorities != null) {
                authorities.addAll(
                        scopeAuthorities
                );
            }

            addClaimAuthorities(
                    jwt.getClaim(
                            "extension_consultarole"
                    ),
                    authorities
            );

            addClaimAuthorities(
                    jwt.getClaim("roles"),
                    authorities
            );

            return authorities;
        };

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }

    private void addClaimAuthorities(
            Object claimValue,
            Set<GrantedAuthority> authorities
    ) {
        if (claimValue instanceof String role) {
            addSingleRole(role, authorities);
            return;
        }

        if (claimValue
                instanceof Collection<?> roles) {

            for (Object role : roles) {
                if (role instanceof String value) {
                    addSingleRole(
                            value,
                            authorities
                    );
                }
            }
        }
    }

    private void addSingleRole(
            String role,
            Set<GrantedAuthority> authorities
    ) {
        if (role == null
                || role.trim().isEmpty()) {
            return;
        }

        String normalized =
                role.trim().toUpperCase();

        String authority =
                normalized.startsWith("ROLE_")
                        ? normalized
                        : "ROLE_" + normalized;

        authorities.add(
                new SimpleGrantedAuthority(
                        authority
                )
        );
    }
}
