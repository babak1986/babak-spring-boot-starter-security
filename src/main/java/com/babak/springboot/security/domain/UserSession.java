package com.babak.springboot.security.domain;

import com.babak.springboot.jpa.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Entity
@Table(name = "b_user_token")
public class UserSession extends BaseEntity<Long> {

    private String token;
    private String ip;
    private Boolean active;
    @ManyToOne
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
