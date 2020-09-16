package com.algorithmvisualizer.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algorithmvisualizer.model.Role;
import com.algorithmvisualizer.model.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByName(RoleType roleName);
}
