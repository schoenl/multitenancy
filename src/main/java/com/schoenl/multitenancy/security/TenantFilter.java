package com.schoenl.multitenancy.security;

import com.schoenl.multitenancy.multitenancy.MultitenancyConfiguration;
import com.schoenl.multitenancy.multitenancy.Tenant;
import com.schoenl.multitenancy.multitenancy.TenantContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private final static String                    TENANT_HEADER = "tenant-identifier";
    private static final List<String>              EXCLUDED_URIS = List.of("/");
    private final        MultitenancyConfiguration multitenancyConfiguration;

    public TenantFilter(MultitenancyConfiguration multitenancyConfiguration) {
        this.multitenancyConfiguration = multitenancyConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String tenantHeader = request.getHeader(TENANT_HEADER);

            Optional<Tenant> optionalTenant = multitenancyConfiguration.tenants()
                    .stream()
                    .filter(tenant -> tenant.name().equals(tenantHeader))
                    .findFirst();

            if (optionalTenant.isEmpty()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            Tenant tenant = optionalTenant.get();
            TenantContextHolder.setTenant(tenant);
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return EXCLUDED_URIS.contains(request.getRequestURI());
    }
}
