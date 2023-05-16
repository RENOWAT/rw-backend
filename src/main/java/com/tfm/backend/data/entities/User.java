package com.tfm.backend.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfm.backend.data.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tfmuser") // conflict with user table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(unique = true, nullable = false)
    private String mobile;
    @NonNull
    private String firstName;
    private String familyName;
    @Column(unique = true, nullable = false)
    private String email;
    private String dni;
    private String address;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime registrationDate;
    private Boolean active;

}
