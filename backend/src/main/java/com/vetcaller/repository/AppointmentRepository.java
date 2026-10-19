package com.vetcaller.repository;

import com.vetcaller.domain.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.sql.Timestamp;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @NativeQuery("""
        SELECT * FROM appointments a
        WHERE a.vet_id = :userId AND a.start_time >= :afterTimestamp
        ORDER BY a.start_time
    """)
    Page<Appointment> findByVetIdAndStartTimeAfter(Long userId, Timestamp afterTimestamp, Pageable pageable);

    @NativeQuery("""
        SELECT * FROM appointments a
        WHERE a.vet_id = :userId
        ORDER BY a.start_time
    """)
    Page<Appointment> findByVetId(Long userId, Pageable pageable);

    @NativeQuery("""
        SELECT 1 FROM appoinments p
            WHERE p.vet_id = :vetId
            AND (
                    (p.date_start < :endTime AND p.date_end > :startTime)
                    OR
                    (p.date_start >= :startTime AND p.date_start < :endTime)
                    OR
                    (p.date_end > :startTime AND p.date_end <= :endTime))
    """)
    boolean isAppointmentTimeSlotAvailable(Long vetId, Timestamp startTime, Timestamp endTime);
}
