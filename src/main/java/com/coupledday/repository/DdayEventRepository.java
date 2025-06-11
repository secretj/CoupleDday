package com.coupledday.repository;

import com.coupledday.domain.dday.DdayEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface DdayEventRepository extends JpaRepository<DdayEvent, Long> {

    List<DdayEvent> findByCoupleIdAndStatusOrderByTargetDateAsc(Long coupleId, EventStatus status);

    @Query("SELECT e FROM DdayEvent e WHERE e.couple.id = :coupleId AND e.targetDate BETWEEN :startDate AND :endDate AND e.status = 'ACTIVE' ORDER BY e.targetDate ASC")
    List<DdayEvent> findUpcomingEvents(@Param("coupleId") Long coupleId,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM DdayEvent e WHERE e.targetDate = :targetDate AND e.status = 'ACTIVE'")
    List<DdayEvent> findEventsByDate(@Param("targetDate") LocalDate targetDate);
}
