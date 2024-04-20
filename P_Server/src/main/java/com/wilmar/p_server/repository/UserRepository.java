package com.wilmar.p_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wilmar.p_server.entity.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);
    @Query(value = "SELECT U FROM UserEntity U WHERE U.fullName LIKE  upper(concat('%',:fullName,'%')) AND U.email LIKE  lower(concat('%',:email,'%'))")
    Page<UserEntity> getUserBy(@Param("fullName")  String fullName, @Param("email") String email, Pageable pageable);

}
