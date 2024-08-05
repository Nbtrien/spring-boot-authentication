package com.example.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class AuthCode {
    @Getter
    @AllArgsConstructor
    public enum AuthRole implements GrantedAuthority {
        MANAGER("ROLE_MANAGER", "MANAGER",
                Set.of(
                        AuthPermission.USER_CREATE,
                        AuthPermission.USER_DELETE,
                        AuthPermission.USER_ACCESS,
                        AuthPermission.USER_EDIT,
                        AuthPermission.USER_SHOW
                )),
        ADMIN("ROLE_ADMIN", "ADMIN",
              Set.of(
                      AuthPermission.USER_CREATE,
                      AuthPermission.USER_ACCESS,
                      AuthPermission.USER_EDIT,
                      AuthPermission.USER_SHOW
              )),
        USER("ROLE_USER", "USER",
             Set.of(
                     AuthPermission.USER_ACCESS,
                     AuthPermission.USER_EDIT
             ));
        private final String code;
        private final String display;
        private final Set<AuthPermission> permissions;

        @Override
        public String getAuthority() {
            return code;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum AuthPermission implements GrantedAuthority {

        USER_CREATE("ROLE_USER_CREATE", "CREATE USER"),
        USER_ACCESS("ROLE_USER_ACCESS", "ACCESS USER"),
        USER_DELETE("ROLE_USER_DELETE", "DELETE USER"),
        USER_EDIT("ROLE_USER_EDIT", "EDIT USER"),
        USER_SHOW("ROLE_USER_SHOW", "SHOW USER");

        private final String code;
        private final String display;

        @Override
        public String getAuthority() {
            return code;
        }
    }
}