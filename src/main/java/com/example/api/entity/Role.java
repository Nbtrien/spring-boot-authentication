package com.example.api.entity;

import com.example.api.code.AuthCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME", unique = true)
    @Enumerated(EnumType.STRING)
    private AuthCode.AuthRole roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "ROLE_PERMISSION", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns =
    @JoinColumn(name = "PERMISSION_ID"))
    private Set<Permission> permissions;
}
