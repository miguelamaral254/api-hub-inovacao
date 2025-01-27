package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    public static final String[] PUBLIC_ROUTES = {
            "/auth/**",
            "/users/create-user"
    };

    public static final String[] USER_ROUTES = {
            "/users/by-email",
            "/users/all-users"
    };

    public static final String[] ADMIN_ROUTES = {
            "/admin/**",
            "/users/all-platform-users",
            "/users/all-users"
    };

    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}
