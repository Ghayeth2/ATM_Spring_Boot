package com.atm.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Builder @Entity @Table(name = "roles")
@Data @NoArgsConstructor @Log4j2
//@AllArgsConstructor
@AttributeOverride(name = "slug", column = @Column(name = "ignored_column",
                    insertable = false, updatable = false))
public class Role extends BaseEntity {
    @Column(nullable = false, length = 25, unique = true)
    private String name;
//    @Transient
//    private String slug;
    public Role(String name){
        this.name = name;
    }
}
