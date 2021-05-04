package com.main.app.domain.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContactDTO {

    private String name;

    private String email;

    private String city;

    private String subject;

    private String message;
}