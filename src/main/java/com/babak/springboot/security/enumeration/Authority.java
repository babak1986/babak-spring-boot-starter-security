package com.babak.springboot.security.enumeration;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
public enum Authority {

    USER("USER_ROLE"), ADMIN("ADMIN_ROLE");

    private String role;

    public String getRole() {
        return this.role;
    }

    Authority(String role) {
        this.role = role;
    }
}
