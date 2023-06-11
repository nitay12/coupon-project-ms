package com.coupons.authservice.tasks;

import com.coupons.authservice.model.ERole;
import com.coupons.authservice.model.Role;
import com.coupons.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class RolesInit implements CommandLineRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) {
        final List<Role> roles = roleRepository.findAll();
        if (roles.size()<1){
            log.info("No roles found, initializing roles...");
            roleRepository.deleteAll();
            for (ERole eRole:
                 ERole.values()) {
                roleRepository.save(Role.builder().name(eRole).build());
                log.info(eRole.name() + " added");
            }
        }
    }
}
