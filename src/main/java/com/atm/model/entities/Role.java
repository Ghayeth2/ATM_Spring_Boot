package com.atm.model.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

@Entity @Builder @Table(name = "roles")
@Data @NoArgsConstructor @Log4j2
public class Role extends BaseEntity {
    @Column(nullable = false, length = 25, unique = true)
    private String name;

    public Role(String name){
        this.name = name;
    }
}
