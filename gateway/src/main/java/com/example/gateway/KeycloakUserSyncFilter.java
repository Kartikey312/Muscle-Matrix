package com.example.gateway;

import com.example.gateway.dto.RegisterRequest;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {

    private final WebClient webClient; // ✅ instead of UserService

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");

        RegisterRequest request = getUserDetails(token);

        if (userId == null && request != null) {
            userId = request.getKeycloakId();
        }

        if (userId != null && token != null) {
            String finalUserId = userId;

            return validateUser(userId)
                    .flatMap(exists -> {
                        if (!exists && request != null) {
                            return registerUser(request);
                        } else {
                            log.info("User already exists, skipping sync");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID", finalUserId)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    }));
        }

        return chain.filter(exchange);
    }

    // ✅ CALL USER SERVICE API
    private Mono<Boolean> validateUser(String userId) {
        return webClient.get()
                .uri("http://localhost:8081/api/users/{id}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private Mono<Void> registerUser(RegisterRequest request) {
        return webClient.post()
                .uri("http://localhost:8081/api/users/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private RegisterRequest getUserDetails(String token) {
        try {
            if (token == null) return null;

            String tokenWithoutBearer = token.replace("Bearer ", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest req = new RegisterRequest();
            req.setEmail(claims.getStringClaim("email"));
            req.setKeycloakId(claims.getStringClaim("sub"));
            req.setPassword("dummy@123123");
            req.setFirstName(claims.getStringClaim("given_name"));
            req.setLastName(claims.getStringClaim("family_name"));

            return req;

        } catch (Exception e) {
            log.error("Error parsing JWT", e); // ✅ FIXED logging
            return null;
        }
    }
}