package com.babak.springboot.security.service;

import com.babak.springboot.jpa.service.BaseService;
import com.babak.springboot.security.domain.User;
import com.babak.springboot.security.domain.UserAuthority;
import com.babak.springboot.security.domain.UserSession;
import com.babak.springboot.security.enumeration.Authority;
import com.babak.springboot.security.exception.BaseSecurityException;
import com.babak.springboot.security.jwt.JwtUtil;
import com.babak.springboot.security.model.UserAuthenticationModel;
import com.babak.springboot.security.model.UserSubmitModel;
import com.babak.springboot.security.repository.UserRepository;
import com.babak.springboot.security.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Service
public class UserService extends BaseService<User, Long, UserRepository> implements UserDetailsService, PasswordEncoder {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Value("${base.security.encoder.secret}")
    private String encoderSecret;

    public UserService(UserRepository repository,
                       JwtUtil jwtUtil,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        super(repository);
        this.jwtUtil = jwtUtil;
        this.request = request;
        this.response = response;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return new Pbkdf2PasswordEncoder(encoderSecret, 16, 1,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256).encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return new Pbkdf2PasswordEncoder(encoderSecret, 16, 1,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256).matches(rawPassword, encodedPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getRepository().findByUsername(username).orElse(null);
    }

    public User login(UserAuthenticationModel authenticationModel) {
        User user = (User) getRepository().findByUsername(authenticationModel.getUsername()).orElse(null);
        if (user != null) {
            if (matches(authenticationModel.getPassword(), user.getPassword())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                String token = jwtUtil.generateToken(user);
                UserSession userSession = new UserSession();
                userSession.setToken(token);
                userSession.setIp(request.getRemoteAddr());
                user.getTokens().add(userSession);
                getRepository().save(user);
                CookieUtil.token(response, token);
                return user;
            }
        }
        throw new BaseSecurityException.InvalidCredentialsException();
    }

    @Transactional
    public User register(UserSubmitModel userSubmitModel, Authority authority, boolean enabled) {
        User user = new User();
        user.setUsername(userSubmitModel.getUsername());
        user.setPassword(encode(userSubmitModel.getPassword()));
        user.setFirstname(userSubmitModel.getFirstname());
        user.setLastname(userSubmitModel.getLastname());
        user.setEmail(user.getEmail());
        user.setMobile(user.getMobile());
        user.setEnabled(enabled);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority(authority.getRole());
        userAuthority.setCurrent(true);
        user.setAuthorities(Set.of(userAuthority));
        return submit(user);
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        CookieUtil.invalidate(response, CookieUtil.JWT_TOKEN_NAME);
    }

    public boolean validate() {
        if (jwtUtil.validate(CookieUtil.get(request, CookieUtil.JWT_TOKEN_NAME))) {
            return true;
        }
        return false;
    }
}
