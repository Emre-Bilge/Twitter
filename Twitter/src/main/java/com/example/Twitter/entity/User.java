package com.example.Twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user" , schema = "twitter")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
private Long id;

    @Column(name="username")
    @NotNull(message = "boş bırakılamaz")
    @NotBlank(message = "boş bırakılamaz")
    @Size(min = 3 , max = 50 , message = "3 ile 50 karatkter arasında olmalıdır")
        private String username;

    @Column(name="password")
    @NotNull(message = "boş bırakılamaz")
    @NotBlank(message = "boş bırakılamaz")
    @Size(min = 3 , max = 50 , message = "3 ile 50 karatkter arasında olmalıdır")
        private String password ;

    @Column(name="first_name")
    @NotNull(message = "boş bırakılamaz")
    @NotBlank(message = "boş bırakılamaz")
    @Size(min = 3 , max = 50 , message = "3 ile 50 karatkter arasında olmalıdır")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",schema = "twitter",joinColumns = @JoinColumn(name="user_id")
            ,inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> authorities = new HashSet<>();


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
private Authentication authentication;

@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
private Set<Tweet> tweets = new HashSet<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
private Set<Like> likes = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }




    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(Set<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }
}
