package com.example.restik.models;


import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
public class user {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
//
//    @UniqueElements(message = "Данное имя уже занято")
//    @NotEmpty(message = "Заполните имя пользователя")
    private String username;
//
//    @NotEmpty(message = "Придумайте пароль")
//    @Size(min=8,message = "В пароле должно быть минимум 8 символов")
    private String password;
    private boolean active;

    private String avatarlink;

    private byte[] avatarbytes;


    @ElementCollection(targetClass = role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<role> getRoles() {
        return roles;
    }

    public void setRoles(Set<role> roles) {
        this.roles = roles;
    }

    public byte[] getAvatarbytes() {
        return avatarbytes;
    }

    public void setAvatarbytes(byte[] avatarbytes) {
        this.avatarbytes = avatarbytes;
    }

    public String getAvatarlink() {
        return avatarlink;
    }

    public void setAvatarlink(String avatarlink) {
        this.avatarlink = avatarlink;
    }
}
