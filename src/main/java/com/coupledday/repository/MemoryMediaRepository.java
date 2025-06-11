package com.coupledday.repository;

import com.coupledday.domain.memory.MemoryMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemoryMediaRepository extends JpaRepository<MemoryMedia, Long> {
    List<MemoryMedia> findByMemoryIdOrderBySortOrder(Long memoryId);
}