package com.test.kopnus.repository;

import com.test.kopnus.model.entity.PekerjaanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PekerjaanRepository extends JpaRepository<PekerjaanEntity, Integer> {
}
