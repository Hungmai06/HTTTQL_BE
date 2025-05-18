package com.example.library.controllers;

import com.example.library.dtos.request.RoleRequestDTO;
import com.example.library.dtos.response.RoleResponse;
import com.example.library.exceptions.DataNotFoundException;
import com.example.library.models.Role;
import com.example.library.services.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping("/")
    public ResponseEntity<List<Role>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@Valid @PathVariable Long id) throws DataNotFoundException {
        Role role = roleService.getRoleById(id);

        return ResponseEntity.ok(RoleResponse.builder()
                        .name(role.getName())
                        .id(role.getId())
                .build());
    }
    @PostMapping("/")
    public ResponseEntity<RoleResponse> createRoles(@Valid @RequestBody RoleRequestDTO roleRequestDTO){
        Role role= roleService.createRole(roleRequestDTO);
        return ResponseEntity.ok(RoleResponse.builder()
                .name(role.getName())
                        .id(role.getId())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@Valid @PathVariable Long id,@Valid @RequestBody RoleRequestDTO roleRequestDTO) throws DataNotFoundException {
        Role role = roleService.updateRole(id,roleRequestDTO);

        return ResponseEntity.ok(RoleResponse.builder()
                        .name(role.getName())
                        .id(role.getId())
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@Valid @PathVariable Long id) throws DataNotFoundException {
           roleService.deleteRole(id);
           return ResponseEntity.ok("Delete successfully with id: "+ id);
    }
}
