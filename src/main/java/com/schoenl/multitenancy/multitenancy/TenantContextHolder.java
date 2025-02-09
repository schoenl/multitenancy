package com.schoenl.multitenancy.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class TenantContextHolder {

    private static final Logger              logger         = LoggerFactory.getLogger(TenantContextHolder.class);
    private static final ThreadLocal<String> TENANT_CONTEXT = new ThreadLocal<>();

    private TenantContextHolder() {
    }

    public static void setTenant(Tenant tenant) {
        Objects.requireNonNull(tenant);
        TENANT_CONTEXT.set(tenant.name());
        logger.debug("Tenant context set to {}", tenant);
    }

    public static String getTenantIdentifier() {
        return TENANT_CONTEXT.get();
    }

    public static void clear() {
        TENANT_CONTEXT.remove();
        logger.debug("Tenant context has been cleared");
    }
}
