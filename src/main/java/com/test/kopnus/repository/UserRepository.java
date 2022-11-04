package com.test.kopnus.repository;

import com.test.kopnus.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getUserEntityByUserNameAndPassword(String userName, String password);
    @Override
    List<UserEntity> findAll();
}
