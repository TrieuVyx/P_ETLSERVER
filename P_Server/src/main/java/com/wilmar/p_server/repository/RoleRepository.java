package com.wilmar.p_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wilmar.p_server.entity.role.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
}
