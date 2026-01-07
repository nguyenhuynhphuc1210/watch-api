package com.example.watch.entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.watch.enums.UserRole;
import com.example.watch.enums.UserStatus;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Thêm nullable = false
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 15) // Nên bắt buộc để giao hàng
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Mặc định nên là ROLE_USER
    private UserRole role = UserRole.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Mặc định là ACTIVE
    private UserStatus status = UserStatus.ACTIVE;
}
