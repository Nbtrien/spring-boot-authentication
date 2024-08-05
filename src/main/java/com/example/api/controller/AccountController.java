package com.example.api.controller;

import com.example.api.dto.account.UpdateAccountRequestDto;
import com.example.api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/access")
    @PreAuthorize("hasAnyRole({'ROLE_USER_ACCESS'})")
    public ResponseEntity<?> access() {
        return ResponseEntity.ok("access user permission for all role");
    }

    @GetMapping("/show")
    @PreAuthorize("hasAnyRole({'ROLE_USER_SHOW'})")
    public ResponseEntity<?> show() {
        return ResponseEntity.ok("show user permission for all admin and manager");
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAnyRole({'ROLE_USER_DELETE'})")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok("delete user permission for manager");
    }

    @GetMapping
    public ResponseEntity<?> getDetail() {
        return ResponseEntity.ok(accountService.getDetail());
    }

    @GetMapping("/management/users")
    @PreAuthorize("hasAnyRole({'ROLE_USER_SHOW'})")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(accountService.getAllUsers());
    }

    @PreAuthorize("hasAnyRole({'ROLE_USER_SHOW'})")
    @GetMapping("/management/admins")
    public ResponseEntity<?> getAllAdmins() {
        return ResponseEntity.ok(accountService.getAllAdmins());
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole({'ROLE_USER_EDIT'})")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateAccountRequestDto requestDto) {
        accountService.update(requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole({'ROLE_USER_DELETE'})")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        accountService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
