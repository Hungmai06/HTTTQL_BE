package com.example.library.services;

import com.example.library.dtos.request.RoleRequestDTO;
import com.example.library.dtos.response.RoleResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Role;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoleService {
    Role getRoleById(Long id) throws DataNotFoundException;
    List<Role> getAllRoles();
    Role createRole(RoleRequestDTO roleRequestDTO);
    Role updateRole(Long id, RoleRequestDTO roleRequestDTO) throws DataNotFoundException;
    void deleteRole(Long id) throws DataNotFoundException;
}
