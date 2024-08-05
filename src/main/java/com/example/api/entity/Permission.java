package com.example.api.entity;

import com.example.api.code.AuthCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PERMISSION")
public class Permission extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PERMISSION_ID")
    private Long permissionId;

    @Column(name = "PERMISSION_NAME", unique = true)
    @Enumerated(EnumType.STRING)
    private AuthCode.AuthPermission permissionName;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Role> roles;
}
