package com.pharmaflow.demo.Entities;

import com.pharmaflow.demo.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean passwordChanged;

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean FirstAdmin = false;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private boolean active = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "'PHARMACIST'")
    private Role role = Role.PHARMACIST;
}
