package com.mamadou.sk.uaaservice.user.entitity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String username;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role", referencedColumnName = "role")}
    )
    private List<Authority> authorities = new ArrayList<>();

    private String password;

    private  boolean enabled;

    private boolean locked;

    private boolean expired;

    /**
     *      Generate setters using builder. Note that JPA expect you use java bean convention 'set',
     *      however hibernate does not have constrain on how you generate your setters.
     */
    public User setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public User setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public User setExpired(boolean expired) {
        this.expired = expired;
        return this;
    }
}
