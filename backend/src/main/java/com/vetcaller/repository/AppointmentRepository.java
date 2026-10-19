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
        WHERE a.vet_id = :userId AND a.start_time > :afterTimestamp
        ORDER BY a.start_time
    """)
    Page<Appointment> findByVetIdAndStartTimeAfter(Long userId, Timestamp afterTimestamp, Pageable pageable);
}
