package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    // Rotas públicas - acessíveis sem autenticação
    public static final String[] PUBLIC_ROUTES = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/users/post" // Define explicitamente como público
    };

    // Rotas de usuários - requerem ROLE_USER ou ROLE_ADMIN
    public static final String[] USER_ROUTES = {
            "/users/**"
    };

    // Rotas de administradores - requerem ROLE_ADMIN
    public static final String[] ADMIN_ROUTES = {
            "/admin/**"
    };

    // Métodos HTTP permitidos publicamente
    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}
