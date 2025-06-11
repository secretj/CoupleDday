package com.coupledday.repository;

import com.coupledday.domain.memory.CoupleMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoupleMemoryRepository extends JpaRepository<CoupleMemory, Long> {
    List<CoupleMemory> findByCoupleIdOrderByMemoryDateDesc(Long coupleId);
    List<CoupleMemory> findByUserIdOrderByCreatedAtDesc(Long userId);
}