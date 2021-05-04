package com.main.app.service.user;

import com.main.app.config.SecurityUtils;
import com.main.app.domain.dto.user.PasswordUpdateDTO;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.user.User;
import com.main.app.elastic.dto.user.UserElasticDTO;
import com.main.app.elastic.repository.user.UserElasticRepository;
import com.main.app.repository.user.UserRepository;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.main.app.static_data.Messages.USER_NOT_EXIST;
import static com.main.app.util.UserUtil.encryptUserPassword;
import static com.main.app.util.UserUtil.validatePassword;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    private final UserElasticRepository userElasticRepository;


    @Override
    public UserDTO getCurrentUserDTO() {
        Optional<User> user = getCurrentUser();

        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }

        return ObjectMapperUtils.map(user.get(), UserDTO.class);
    }

    @Override
    public User updateCurrentUser(UserDTO userDTO) {
        Optional<User> user = getCurrentUser();

        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }

        User foundUser = user.get();
        foundUser.setAddress(userDTO.getAddress());
        foundUser.setName(userDTO.getName());
        foundUser.setSurname(userDTO.getSurname());
        foundUser.setEmail(userDTO.getEmail());
        foundUser.setCity(userDTO.getCity());
        foundUser.setPhoneNumber(userDTO.getPhoneNumber());
        foundUser.setPostalCode(userDTO.getPostalCode());

        User savedUser = userRepository.save(foundUser);
        userElasticRepository.save(new UserElasticDTO(savedUser));

        return savedUser;
    }

    @Override
    public Optional<User> getCurrentUser() {
        Optional<String> username = SecurityUtils.getCurrentUserLogin();

        if(!username.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }

        return this.userRepository.findOneByEmailAndDeletedFalse(username.get());
    }

    @Override
    public void updatePassword(PasswordUpdateDTO passwordUpdateDTO) {
        User user = getCurrentUser().get();
        if(BCrypt.checkpw(passwordUpdateDTO.getOldPassword(), user.getPassword())){
            user.setPassword(encryptUserPassword(passwordUpdateDTO.getNewPassword()));
            userRepository.save(user);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
