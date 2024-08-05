package com.example.api;

import com.example.api.code.AuthCode;
import com.example.api.entity.Permission;
import com.example.api.entity.Role;
import com.example.api.repository.PermissionRepository;
import com.example.api.repository.RoleRepository;
import com.example.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringAuthApplication {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthApplication.class, args);
    }

//    @Bean
//    @Transactional
//    public CommandLineRunner commandLineRunner() {
//        return args -> {
//            for (AuthCode.AuthPermission authPermission : AuthCode.AuthPermission.values()) {
//                permissionRepository.saveAndFlush(Permission.builder().permissionName(authPermission).build());
//            }
//
//            for (AuthCode.AuthRole authRole : AuthCode.AuthRole.values()) {
//                Set<Permission> permissions = authRole.getPermissions().stream().map(authPermission -> {
//                    Permission permission = permissionRepository.findByPermissionName(authPermission);
//                    return permission;
//                }).collect(Collectors.toSet());
//
//                Role role = Role.builder().roleName(authRole).permissions(permissions).build();
//
//                roleRepository.save(role);
//            }
//        };
//    }

}
