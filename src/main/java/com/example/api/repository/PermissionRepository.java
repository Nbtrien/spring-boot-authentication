package com.example.api.repository;

import com.example.api.code.AuthCode;
import com.example.api.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByPermissionName(AuthCode.AuthPermission permission);
}
