package com.main.app.domain.dto.user;

import lombok.*;

/**
 * The dto used for user registration data through API.
 *
 * @author Nikola
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String name;

    private String surname;

    private String email;

    private String password;

    private String passwordRepeat;

    private String address;

    private String city;

    private String postalCode;

    private String phoneNumber;

    private boolean newsLetter;

}
