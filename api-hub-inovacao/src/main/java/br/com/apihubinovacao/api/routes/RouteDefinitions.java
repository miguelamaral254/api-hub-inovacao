package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    // Rotas públicas (acessíveis sem autenticação)
    public static final String[] PUBLIC_ROUTES = {
            "/auth/**",  // Login e autenticação
            "/users/create-user-cpf"  // Criar usuários com CPF (público)
    };

    // Rotas acessíveis para usuários autenticados
    public static final String[] USER_ROUTES = {
            "/users/by-email", // Buscar usuário pelo e-mail (qualquer usuário autenticado)

    };

    // Rotas acessíveis apenas para administradores
    public static final String[] ADMIN_ROUTES = {
            "/admin/**",
            "/users/all-platform-users", // Buscar todos os usuários paginados
            "/users/create-user-cnpj" // Criar usuários com CNPJ (apenas Admin)
    };

    // Métodos HTTP permitidos para rotas públicas
    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}