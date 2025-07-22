package com.spring.backend.repository;

import com.spring.backend.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> ,JpaSpecificationExecutor<User>{

    @Query(value = "SELECT ?1 FROM DUAL", nativeQuery = true)
    String encodePassword(String rawPassword);

    Optional<User> findByUsername(String userId);


}
