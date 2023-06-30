package com.KurdDevs.KurdDevs.Repo;

import com.KurdDevs.KurdDevs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByActivationToken(String activationToken);
}

