package com.test.kopnus.repository;

import com.test.kopnus.model.entity.PekerjaanUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PekerjaanUserRepository extends JpaRepository<PekerjaanUserEntity, Integer> {

    @Override
    List<PekerjaanUserEntity> findAll();

    List<PekerjaanUserEntity> findPekerjaanUserEntitiesByUserId(Integer userId);
}
