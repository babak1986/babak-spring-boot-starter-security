package com.babak.springboot.security.repository;

import com.babak.springboot.jpa.repository.BaseRepository;
import com.babak.springboot.security.domain.UserSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: Babak Behzadi
 * Email: behzadi.babak@gmail.com
 **/
@Repository
public interface UserSessionRepository extends BaseRepository<UserSession, Long> {

    @Query(value = "select us from UserSession us where us.token=:token and us.active=true")
    Optional<UserSession> findActive(String token);
}
