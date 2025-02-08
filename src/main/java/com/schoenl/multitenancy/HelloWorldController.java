package com.schoenl.multitenancy;

import com.schoenl.multitenancy.multitenancy.Tenant;
import com.schoenl.multitenancy.multitenancy.TenantContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/my-tenant")
    public ResponseEntity<Tenant> getTenant() {
        return ResponseEntity.ok(TenantContextHolder.getTenant());
    }


}
