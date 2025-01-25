package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    public static final String[] PUBLIC_ROUTES = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/users/post"
    };

    public static final String[] USER_ROUTES = {
            "/users/**"
    };

    public static final String[] ADMIN_ROUTES = {
            "/admin/**"
    };

    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}
