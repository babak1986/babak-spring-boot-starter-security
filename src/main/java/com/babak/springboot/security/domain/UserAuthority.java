package com.babak.springboot.security.domain;

import com.babak.springboot.jpa.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Entity
@Table(name = "b_user_authority")
public class UserAuthority extends BaseEntity<Long> implements GrantedAuthority {

    private String authority;
    private Boolean current;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public UserAuthority() {
    }

    public UserAuthority(String authority, Boolean current) {
        this.authority = authority;
        this.current = current;
    }
}