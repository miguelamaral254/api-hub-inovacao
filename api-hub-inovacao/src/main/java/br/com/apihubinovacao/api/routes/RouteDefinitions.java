package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    // Rotas públicas (acessíveis sem autenticação)
    public static final String[] PUBLIC_ROUTES = {
            "/auth/**",  // Login e autenticação
            "/users/create-user-cpf"  ,
            "/users/all-users",
            "/users/create-user-cnpj"
    };

    // Rotas acessíveis para usuários autenticados
    public static final String[] USER_ROUTES = {
            "/users/by-email",
            "/projects/create",
            "/projects/all",

    };

    // Rotas acessíveis apenas para administradores
    public static final String[] ADMIN_ROUTES = {
            "/admin/**",
            "/users/all-platform-users",
            "/admin/create-manager"
    };

    // Métodos HTTP permitidos para rotas públicas
    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}