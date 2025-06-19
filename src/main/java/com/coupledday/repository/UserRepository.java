package com.coupledday.repository;

import com.coupledday.domain.common.SocialProvider;
import com.coupledday.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByProviderAndProviderId(SocialProvider provider, String providerId);
    boolean existsByEmail(String email);
}