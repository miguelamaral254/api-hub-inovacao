package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    public static final String[] PUBLIC_ROUTES = {
            "swagger-ui/index.html",
            "/auth/**",
            "/projects/**",
    };
    public static final String[] USER_ROUTES = {

    };
    public static final String[] COMPANY_ROUTES = {
    };
    public static final String[]  MANAGERS_ROUTES = {
    };
    public static final String[] ADMIN_ROUTES = {
    };
    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}