package com.test.kopnus.repository;

import com.test.kopnus.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getUserEntityByUserNameAndPassword(String username, String password);
}
