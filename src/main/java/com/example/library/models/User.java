package com.example.library.models;

import com.example.library.constants.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone",nullable = false)
    private String phone;
    @Column(name = "user_name",nullable = false)
    private String username;
    @Column(name = "email",nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;
    @Column
    private Float deposit;

   @OneToMany(mappedBy = "user")
    private List<BookLoan> bookLoan;

   @ManyToOne
   @JoinColumn(name = "role_id")
   private Role role;

}
