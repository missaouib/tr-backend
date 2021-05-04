package com.main.app.domain.dto.user;


import com.main.app.enums.Role;
import lombok.*;

import java.time.Instant;

/**
 * The dto used for exposing user data through API.
 *
 * @author Nikola
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String email;

    private String name;

    private String surname;

    private String password;

    private String phoneNumber;

    private Instant dateCreated;

    private String address;

    private String city;

    private String postalCode;

    private Instant birthDate;

    private Role role;

    private boolean registrationConfirmed;


}
