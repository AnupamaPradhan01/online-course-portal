package com.example.online_course_portal.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    private boolean enabled=true;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles=new HashSet<>();

    public User() { // Add this no-argument constructor
        // Default constructor needed by JPA/Hibernate
    }

    public User(String name, String email, String password, boolean enabled, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    //Helper method to add a role(useful during user creation/role assignment)
    public void addRole(Role role){
        this.roles.add(role);
    }
    //<------------------------ADD THIS CRUCIAL METHOD----------------->
    /**
     * Checks if the user has a specific role by its name.
     * This methods assumes role names are stored exactly as strings
     * (e.g. "ROLE_TEACHER","ROLE_STUDENT")
     *
     * @param roleName THe extract string name of the role to check for.
     * @return true if the user has the specified role, false otherwise
     *
     */
    public boolean hasRole(String roleName){
        if(this.roles==null || this.roles.isEmpty()){
            return false;
        }
        return this.roles.stream().anyMatch(role->role.getName().equals(roleName));
    }

}
