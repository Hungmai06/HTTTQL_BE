package com.example.library.repositories;

import com.example.library.dtos.response.RoleResponse;
import com.example.library.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
