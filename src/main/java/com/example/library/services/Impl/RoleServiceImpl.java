package com.example.library.services.Impl;

import com.example.library.dtos.request.RoleRequestDTO;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Role;
import com.example.library.repositories.RoleRepository;
import com.example.library.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long id) throws DataNotFoundException {
        return roleRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Role not found")
        );
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(RoleRequestDTO roleRequestDTO) {
        Role role = Role.builder()
                .name(roleRequestDTO.getName())
        .build();
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, RoleRequestDTO roleRequestDTO) throws DataNotFoundException {
        Role role = roleRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("Role not found!")
        );
        role.setName(roleRequestDTO.getName());
        roleRepository.save(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) throws DataNotFoundException {
        roleRepository.deleteById(id);
    }
}
