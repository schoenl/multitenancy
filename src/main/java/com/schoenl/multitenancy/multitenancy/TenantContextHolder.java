package com.schoenl.multitenancy.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TenantContextHolder {

    private static final Logger              logger         = LoggerFactory.getLogger(TenantContextHolder.class);
    private static final ThreadLocal<Tenant> TENANT_CONTEXT = new ThreadLocal<>();

    private TenantContextHolder() {
    }

    public static void setTenant(Tenant tenant) {
        TENANT_CONTEXT.set(tenant);
        logger.debug("Tenant context set to {}", tenant);
    }

    public static Tenant getTenant() throws IllegalStateException {
        if (TENANT_CONTEXT.get() == null) {
            throw new IllegalStateException("Tenant context not set");
        }
        return TENANT_CONTEXT.get();
    }

    public static void clear() {
        TENANT_CONTEXT.remove();
        logger.debug("Tenant context has been cleared");
    }
}
