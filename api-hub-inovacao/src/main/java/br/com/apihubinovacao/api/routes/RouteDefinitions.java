package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    // Rotas públicas (acessíveis sem autenticação)
    public static final String[] PUBLIC_ROUTES = {
            "/auth/**",
            "/users/create-user-cpf",
            "/users/all-users",
            "/users/create-user-cnpj",
            "/projects/all",
            "/publish/all"
    };

    // Rotas acessíveis para usuários autenticados
    public static final String[] USER_ROUTES = {
            "/users/by-email",
            "/projects/by-email",
            "/projects/student/create",
            "/projects/professor/create",
            "/projects/all-student",
            "/projects/all-professor",
            "/projects/{projectId}/details",

    };

    // Rotas acessíveis apenas para administradores
    public static final String[] ADMIN_ROUTES = {
            "/admin/**",
            "/users/all-platform-users",
            "/admin/create-manager",
            "/projects/{projectId}/status",
            "/publish/create-publish",
    };

    // Métodos HTTP permitidos para rotas públicas
    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}