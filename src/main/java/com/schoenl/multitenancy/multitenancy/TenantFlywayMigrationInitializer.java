package com.schoenl.multitenancy.multitenancy;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TenantFlywayMigrationInitializer implements InitializingBean, Ordered {

    private static final String TENANT_MIGRATION_LOCATION = "db/migration/tenant";

    private final DataSource             dataSource;
    private final Flyway                 defaultFlyway;
    private final MultitenancyProperties multitenancyProperties;

    public TenantFlywayMigrationInitializer(DataSource dataSource, Flyway defaultFlyway, MultitenancyProperties multitenancyProperties) {
        this.dataSource = dataSource;
        this.defaultFlyway = defaultFlyway;
        this.multitenancyProperties = multitenancyProperties;
    }

    @Override
    public void afterPropertiesSet() {
        multitenancyProperties.tenants().forEach(tenant -> {
            Flyway flyway = tenantFlyway(tenant.name());
            flyway.migrate();
        });

    }

    private Flyway tenantFlyway(String schema) {
        return Flyway.configure()
                .configuration(defaultFlyway.getConfiguration())
                .locations(TENANT_MIGRATION_LOCATION)
                .dataSource(dataSource)
                .schemas(schema)
                .load();
    }

    @Override
    public int getOrder() {
        // Executed after the default schema initialization in FlywayMigrationInitializer.
        return 1;
    }
}