package com.vetcaller.repository;

import com.vetcaller.domain.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findAll(Pageable pageable);

    @NativeQuery("""
        SELECT * FROM pets p
        WHERE p.owner_id = :id
    """)
    Page<Pet> findByOwner(Long id, Pageable pageable);

    List<Pet> findByOwner(Long id);

}
