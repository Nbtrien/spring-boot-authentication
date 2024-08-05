package com.example.api.security;

import com.example.api.entity.Account;
import com.example.api.entity.Permission;
import com.example.api.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class UserPrincipal extends User {
    private Long id;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities,
                         Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public static UserPrincipal create(Account account) {
        if (account == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserPrincipal(account.getEmail(), account.getPassword(), getAuthorities(account.getRoles()),
                                 account.getAccountId());
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return getGrantedAuthorities(getPermission(roles));
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    private static List<String> getPermission(Set<Role> roles) {
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            permissions.add(role.getRoleName().getCode());
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add(item.getPermissionName().getCode());
        }
        return permissions;
    }
}
