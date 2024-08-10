package com.babak.springboot.security.controller;

import com.babak.springboot.security.exception.BaseSecurityException;
import com.babak.springboot.security.model.UserAuthenticationModel;
import com.babak.springboot.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@RestController
@RequestMapping(value = "base/security")
public final class BaseSecurityController {

    private final UserService userService;

    public BaseSecurityController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserAuthenticationModel authenticationModel) {
        userService.login(authenticationModel);
        return ResponseEntity.ok("");
    }

    @GetMapping("logout")
    public ResponseEntity logout() {
        userService.logout();
        return ResponseEntity.ok("");
    }

    @PostMapping("validate")
    public ResponseEntity validate() {
        if (userService.validate()) {
            return ResponseEntity.ok("");
        }
        throw new BaseSecurityException.InvalidCredentialsException();
    }
}
