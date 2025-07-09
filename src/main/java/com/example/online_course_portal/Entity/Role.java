package com.example.online_course_portal.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    //DEFINE FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    //DEFINE CONSTRUCTORS
    public Role(){

    }
    public Role(String name) {
        this.name = name;
    }

    //DEFINE GETTERS AND SETTERS

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //DEFINE TOSTRING

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
