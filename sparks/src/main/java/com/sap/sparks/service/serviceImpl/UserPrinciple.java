package com.sap.sparks.service.serviceImpl;

import com.sap.sparks.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userName;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private String bankAccount;

    private String city;

    private boolean isValid;

    private String phone;

    private String licenseUrl;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(String userName, String password, String email, String firstName, String lastName, String bankAccount, String city, boolean isValid, String phone, String licenseUrl, Collection<? extends GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
        this.city = city;
        this.isValid = isValid;
        this.phone = phone;
        this.licenseUrl = licenseUrl;
        this.authorities = authorities;
    }

    public static UserPrinciple build(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getTypeUser())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBankAccount(),
                user.getCity(),
                user.isValid(),
                user.getPhone(),
                user.getLicenseUrl(),
                authorities
        );
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getCity() {
        return city;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}