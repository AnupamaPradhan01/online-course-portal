package com.example.online_course_portal.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // <-- IMPORTANT: Add this import

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors; // <-- Add this import for stream.Collectors

@Entity
@Table(name="users")
public class User implements UserDetails { // <-- Implement UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email; // This will serve as the username for Spring Security

    private String password;

    private boolean enabled = true; // Default to true

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
        // Default constructor needed by JPA/Hibernate
    }

    public User(String name, String email, String password, boolean enabled, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    // --- Getters and Setters for your entity fields (keep as they are) ---
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public boolean hasRole(String roleName){
        if(this.roles==null || this.roles.isEmpty()){
            return false;
        }
        return this.roles.stream().anyMatch(role->role.getName().equals(roleName));
    }


    // --- Implementations of UserDetails interface methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map your 'roles' Set to Spring Security's GrantedAuthority objects
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password; // Return the password stored in your entity
    }

    @Override
    public String getUsername() {
        return this.email; // Use email as the username for Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // For simplicity, assume accounts don't expire. Implement your logic if needed.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // For simplicity, assume accounts are not locked. Implement your logic if needed.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // For simplicity, assume credentials don't expire. Implement your logic if needed.
    }

    @Override
    public boolean isEnabled() {
        return this.enabled; // Return the 'enabled' status from your entity
    }
}