package com.main.app.service.user;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user.PasswordUpdateDTO;
import com.main.app.domain.dto.user.RegisterRequestDTO;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * The service used for management of the User data.
 *
 * @author Nikola
 */
public interface UserService {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    User save(User user);

    User adminUserSave(UserDTO user);

    User edit(User user, Long id);

    User delete(Long id);

    Entities getAll();

    User getOne(Long id);

    void createPassword(String password, String registrationToken);

    void sendRecoveryPasswordMail(String email);

    void resetPassword(String password, String registrationToken);

    void changePassword(String email, String password);

    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    User register(RegisterRequestDTO registerRequestDTO);

}
