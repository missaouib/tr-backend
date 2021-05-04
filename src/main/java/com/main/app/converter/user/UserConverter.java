package com.main.app.converter.user;

import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static User userDTOtoUserEntity(UserDTO userDTO){
        return User
                .builder()
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .address(userDTO.getAddress())
                .phoneNumber(userDTO.getPhoneNumber())
                .birthDate(userDTO.getBirthDate())
                .build();
    }

    public static UserDTO userEntityToUserDTO(User user){
        return UserDTO
                .builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
//                .address(user.getAddress())
//                .city(user.getCity())
//                .postalCode(user.getPostalCode())
//                .phoneNumber(user.getPhoneNumber())
                .dateCreated(user.getDateCreated())
                .role(user.getRole())
                .build();
    }

    public static List<UserDTO> usersListToUsersDTOList(List<User> users) {
        return users
                .stream()
                .map(user -> userEntityToUserDTO(user))
                .collect(Collectors.toList());
    }

}
