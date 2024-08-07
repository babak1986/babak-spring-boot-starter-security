package com.babak.springboot.security.repository;

import com.babak.springboot.jpa.repository.BaseRepository;
import com.babak.springboot.security.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<UserDetails> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}