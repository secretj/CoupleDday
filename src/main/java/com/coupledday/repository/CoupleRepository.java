package com.coupledday.repository;

import com.coupledday.domain.couple.Couple;
import com.coupledday.domain.couple.CoupleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

    @Query("SELECT c FROM Couple c WHERE (c.user1Id = :userId OR c.user2Id = :userId) AND c.status = 'ACTIVE'")
    Optional<Couple> findActiveCoupleByUserId(@Param("userId") Long userId);

    Optional<Couple> findByCoupleCodeAndStatus(String coupleCode, CoupleStatus status);
}