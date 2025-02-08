package com.schoenl.multitenancy.multitenancy;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;

@ConfigurationProperties(prefix = "multitenancy")
@Validated
public record MultitenancyConfiguration(@NotEmpty HashSet<Tenant> tenants) {
}
