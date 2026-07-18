package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends JpaRepository<Sequence, String> {
}