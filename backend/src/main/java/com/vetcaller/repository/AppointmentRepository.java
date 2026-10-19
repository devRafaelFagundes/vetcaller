package com.vetcaller.repository;

import com.vetcaller.domain.Appointment;
import com.vetcaller.domain.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.sql.Timestamp;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

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

    @NativeQuery("""
        SELECT * FROM appointments a
        WHERE a.vet_id = :userId AND a.start_time >= :afterTimestamp
            AND a.status = :status
        ORDER BY a.start_time
    """)
    Page<Appointment> findByVetIdAndStartTimeAfterAndStatus(Long userId, Timestamp afterTimestamp, AppointmentStatus status, Pageable pageable);

    @NativeQuery("""
        SELECT * FROM appointments a
        WHERE a.vet_id = :userId AND a.status = :status
        ORDER BY a.start_time
    """)
    Page<Appointment> findByVetIdAndStatus(Long userId, AppointmentStatus status, Pageable pageable);
}
