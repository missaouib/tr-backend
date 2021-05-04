package com.main.app.service.user;

import com.main.app.domain.dto.user.PasswordUpdateDTO;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;

import java.util.Optional;

public interface CurrentUserService {

    UserDTO getCurrentUserDTO();
    Optional<User> getCurrentUser();
    User updateCurrentUser(UserDTO userDTO);
    void updatePassword(PasswordUpdateDTO passwordUpdateDTO);

}
