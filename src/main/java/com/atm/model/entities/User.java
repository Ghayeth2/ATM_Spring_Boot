package com.atm.model.entities;

import javax.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

@Data @Entity @Table(name = "users")
@NamedEntityGraph @Builder
@Log4j2 @NoArgsConstructor
public class User extends BaseEntity {
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"
    ), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    // private Collection<Role> roles;

    public User(String firstName, String lastName, String email, String password, List<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
