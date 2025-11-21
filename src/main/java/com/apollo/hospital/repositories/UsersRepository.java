package com.apollo.hospital.repositories;

import com.apollo.hospital.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    // find user by email AND active flag
    Optional<Users> findByEmailIgnoreCaseAndIsActive(String email, Boolean isActive);

    Optional<Users> findByEmail(String email);

    @Query(value = "select * from tbl_users u where u.email = :email and u.is_active = true", nativeQuery = true)
    Optional<Users> findByEmailAndIsActive(@Param("email") String email);

}
