package com.test.kopnus.repository;

import com.test.kopnus.model.entity.PekerjaanUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PekerjaanUserRepository extends JpaRepository<PekerjaanUserEntity, Integer> {
}
