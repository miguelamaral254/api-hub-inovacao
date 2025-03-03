package br.com.apihubinovacao.api.routes;

import org.springframework.http.HttpMethod;

public class RouteDefinitions {

    public static final String[] PUBLIC_ROUTES = {
            "swagger-ui/index.html",
            "/auth/**",
            "/projects",
            "/users/create-user-cpf",
            "/users/all-users",
            "/users",
            "/users/create-user-cnpj",
            "/projects/all",
            "/opportunities/approved/active",
            "/publish/all",
            "/opportunities/all",
            "/users/all-platform-users",
            "/startup/all",
            "publish/{publishId}/update",
            "/opportunities/company/{companyName}",
            "/opportunities/manager/all"




    };
    public static final String[] USER_ROUTES = {
            "/users/by-email",
            "/projects/by-email",
            "/projects/student/create",
            "/projects/professor/create",
            "/projects/all-student",
            "/projects/all-professor",
            "/projects/{projectId}/details",
            "/startup/student/create",
            "/startup/student/startups",
            "/startup/professor/create",
            "/startup/professor/startups",
            "/{startupId}/details",



    };
    public static final String[] COMPANY_ROUTES = {
            "/opportunities/create",
            "/{opportunityId}/details",
            "/opportunities/{opportunityId}/details",

    };
    public static final String[]  MANAGERS_ROUTES = {
            "/projects/{projectId}/status",
            "/opportunities/{opportunityId}/status",
            "/projects/manager/all",
            "/startup/by-email",
            "/startup/{startupId}/status",
            "/publish/create",



    };
    public static final String[] ADMIN_ROUTES = {
            "/admin/**",
            "/admin/create-manager",
            "/projects/{projectId}/status",
            "/opportunities/{opportunityId}/status",
            "/startup//by-email",


    };
    public static final HttpMethod[] PUBLIC_HTTP_METHODS = {
            HttpMethod.GET, HttpMethod.POST
    };
}