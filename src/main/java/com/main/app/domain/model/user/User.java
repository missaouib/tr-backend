package com.main.app.domain.model.user;

import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.user_favourites.UserFavourites;
import com.main.app.domain.model.variation.Variation;
import com.main.app.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private Instant birthDate;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiration_date", columnDefinition = "DATE")
    private Instant resetTokenExpirationDate;

    @Column(name = "registration_token")
    private String registrationToken;

    @Column(name = "registration_token_expiration_date", columnDefinition = "DATE")
    private Instant registrationTokenExpirationDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "registration_confirmed")
    private boolean registrationConfirmed;


    public User(@NotNull String password, @NotNull String email, @NotNull String name, @NotNull String surname) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.registrationConfirmed = false;
    }

    public User(UserDTO userDTO) {
//        this.setId(userDTO.getId());
        this.setEmail(userDTO.getEmail());
        this.setName(userDTO.getName());
        this.setPassword(userDTO.getPassword());
        this.setSurname(userDTO.getSurname());
        this.setPhoneNumber(userDTO.getPhoneNumber());
        this.setRole(userDTO.getRole());
        this.setAddress(userDTO.getAddress());
        this.setBirthDate(userDTO.getBirthDate());
        this.setRegistrationConfirmed(userDTO.isRegistrationConfirmed());
    }
}