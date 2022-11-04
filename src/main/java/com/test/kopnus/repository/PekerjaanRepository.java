package com.test.kopnus.repository;

import com.test.kopnus.model.entity.PekerjaanEntity;
import com.test.kopnus.model.entity.PekerjaanUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PekerjaanRepository extends JpaRepository<PekerjaanEntity, Integer> {
    @Override
    List<PekerjaanEntity> findAll();
}
