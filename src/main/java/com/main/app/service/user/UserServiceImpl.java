package com.main.app.service.user;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user.PasswordUpdateDTO;
import com.main.app.domain.dto.user.RegisterRequestDTO;
import com.main.app.domain.dto.user.UserDTO;
//import com.main.app.domain.model.newsletter.Newsletter;
import com.main.app.domain.model.user.User;
import com.main.app.elastic.dto.user.UserElasticDTO;
import com.main.app.elastic.repository.user.UserElasticRepository;
import com.main.app.elastic.repository.user.UserElasticRepositoryBuilder;
import com.main.app.enums.Role;
import com.main.app.exception.InvalidPasswordException;
import com.main.app.exception.TokenExpiredException;
import com.main.app.repository.user.UserRepository;
import com.main.app.service.email.PasswordRecoveryEmailService;
import com.main.app.service.email.RegistrationEmailService;
import com.main.app.static_data.Constants;
import com.main.app.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.user.UserConverter.usersListToUsersDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.UserUtil.*;
import static com.main.app.util.Util.adminUsersToIds;
import static com.main.app.util.Util.usersToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${spring.deeplink}")
    private String deeplinkUrl;

    private final UserRepository userRepository;

    private final UserElasticRepository userElasticRepository;

    private final RegistrationEmailService registrationEmailService;

    private final PasswordRecoveryEmailService passwordRecoveryEmailService;

    private final UserElasticRepositoryBuilder userElasticRepositoryBuilder;



    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findOneByEmailAndDeletedFalse(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findOneByIdAndDeletedFalse(id);
    }

    @Override
    public User save(User user) {
        Optional<User> oneUser = userRepository.findOneByEmailAndDeletedFalse(user.getEmail());

        if (oneUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_WITH_EMAIL_ALREADY_EXIST);
        }

        if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EMAIL_CANT_BE_NULL);
        }

        if (user.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NAME_CANT_BE_NULL);
        }

        if (user.getSurname() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_SURNAME_CANT_BE_NULL);
        }

        if (user.getPhoneNumber() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_PHONE_CANT_BE_NULL);
        }

        user.setRegistrationToken(Util.generateUniqueString());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, Constants.VALIDITY_OF_TOKEN_IN_DAYS);
        user.setRegistrationTokenExpirationDate(c.getTime().toInstant());

        User savedUser = userRepository.save(user);
        userElasticRepository.save(new UserElasticDTO(savedUser));

//        registrationEmailService.sendEmail(
//                deeplinkUrl,
//                "?registrationToken=" + user.getRegistrationToken(),
//                emailFrom,
//                user.getEmail(),
//                Constants.URL_PART_USER
//        );
        return savedUser;
    }

    @Override
    public User adminUserSave(UserDTO user) {
        Optional<User> oneUser = userRepository.findOneByEmailAndDeletedFalse(user.getEmail());

        if (oneUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_WITH_EMAIL_ALREADY_EXIST);
        }

        if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EMAIL_CANT_BE_NULL);
        }

        if(!validateEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EMAIL_NOT_VALID);
        }

        if (user.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NAME_CANT_BE_NULL);
        }

        if (user.getSurname() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_SURNAME_CANT_BE_NULL);
        }
        User userToSave = new User();
        userToSave.setEmail(user.getEmail());
        userToSave.setName(user.getName());
        userToSave.setSurname(user.getSurname());
        userToSave.setPassword(encryptUserPassword("123"));
        userToSave.setDateCreated(Calendar.getInstance().toInstant());
        userToSave.setDateUpdated(Calendar.getInstance().toInstant());
        userToSave.setRegistrationConfirmed(true);
        userToSave.setRole(Role.ROLE_USER);
        userToSave.setRegistrationToken(Util.generateUniqueString());
        userToSave.setPhoneNumber("XXX-XXX-XXX");

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, Constants.VALIDITY_OF_TOKEN_IN_DAYS);
        userToSave.setRegistrationTokenExpirationDate(c.getTime().toInstant());

        User savedUser = userRepository.save(userToSave);
        userElasticRepository.save(new UserElasticDTO(savedUser));

        registrationEmailService.sendEmail(
                deeplinkUrl,
                "?registrationToken=" + userToSave.getRegistrationToken(),
                emailFrom,
                user.getEmail(),
                Constants.URL_PART_USER
        );
        return savedUser;
    }

    @Override
    public User edit(User user, Long id) {
        Optional<User> optionalFoundUser = userRepository.findOneByIdAndDeletedFalse(id);

        if (!optionalFoundUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }

        User foundUser = optionalFoundUser.get();

        if (user.getName() != null) {
            foundUser.setName(user.getName());
        }

        if (user.getSurname() != null) {
            foundUser.setSurname(user.getSurname());
        }

        if (user.getPhoneNumber() != null) {
            foundUser.setPhoneNumber(user.getPhoneNumber());
        }

        if (user.getEmail() != null) {
            foundUser.setEmail(user.getEmail());
        }

        if (user.getAddress() != null) {
            foundUser.setAddress(user.getAddress());
        }

        if (user.getBirthDate() != null) {
            foundUser.setBirthDate(user.getBirthDate());
        }

        User savedUser = userRepository.save(foundUser);
        userElasticRepository.save(new UserElasticDTO(savedUser));

        return savedUser;
    }

    public User delete(Long id) {
        Optional<User> optionalUser = userRepository.findOneByIdAndDeletedFalse(id);

        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }

        User foundUser = optionalUser.get();
        foundUser.setDeleted(true);
        foundUser.setDateDeleted(Calendar.getInstance().toInstant());

        User savedUser = userRepository.save(foundUser);
        userElasticRepository.save(new UserElasticDTO(savedUser));

        return savedUser;
    }

    @Override
    public Entities getAll() {
        List<User> users = userRepository.findAllByDeletedFalse();
        List<UserDTO> usersDTO = usersListToUsersDTOList(users);

        Entities entities = new Entities();
        entities.setEntities(usersDTO);
        entities.setTotal(users.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable) {

        if(searchParam == "" || searchParam == null ){
            Page<User> pagedUserss = userRepository.findAllByDeletedFalse(pageable);
            List<Long> ids = adminUsersToIds(pagedUserss);

            Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            List<User> users = userRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

            //List<UserDTO> usersDTO = usersListToUsersDTOList(users);

            Entities entities = new Entities();
            entities.setEntities(users);
            entities.setTotal(pagedUserss.getTotalElements());

            return entities;
        }else{
            Page<UserElasticDTO> pagedUsers = userElasticRepository.search(userElasticRepositoryBuilder.generateQuery(searchParam), pageable);

            List<Long> ids = usersToIds(pagedUsers);

            Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            List<User> users = userRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

            //List<UserDTO> usersDTO = usersListToUsersDTOList(users);

            Entities entities = new Entities();
            entities.setEntities(users);
            entities.setTotal(pagedUsers.getTotalElements());

            return entities;

        }
    }

    @Override
    public User register(RegisterRequestDTO registerRequestDTO) {
        Optional<User> optionalUser = userRepository.findOneByEmailAndDeletedFalse(registerRequestDTO.getEmail());
        System.out.println("regitering the user");
        if(optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_WITH_EMAIL_ALREADY_EXIST);
        }

        if(registerRequestDTO.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NAME_CANT_BE_NULL);
        }

        if(registerRequestDTO.getSurname() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_SURNAME_CANT_BE_NULL);
        }

        if(registerRequestDTO.getEmail() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EMAIL_CANT_BE_NULL);
        }

        if(!validateEmail(registerRequestDTO.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EMAIL_NOT_VALID);
        }

        if(registerRequestDTO.getPassword() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_PASSWORD_CANT_BE_NULL);
        }

        if(registerRequestDTO.getPasswordRepeat() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_PASSWORD_REPEAT_CANT_BE_NULL);
        }

//        if(registerRequestDTO.getAddress() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_ADDRESS_CANT_BE_NULL);
//        }
//
//        if(registerRequestDTO.getCity() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_CITY_CANT_BE_NULL);
//        }
//
//        if(registerRequestDTO.getPostalCode() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_POSTAL_CODE_CANT_BE_NULL);
//        }
//
//        if(registerRequestDTO.getPhoneNumber() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_PHONE_NUMBER_CANT_BE_NULL);
//        }

        if(registerRequestDTO.getPassword().equals(registerRequestDTO.getPasswordRepeat())) {
            User user = new User();
            user.setName(registerRequestDTO.getName());
            user.setSurname(registerRequestDTO.getSurname());
            user.setEmail(registerRequestDTO.getEmail());
            user.setPassword(encryptUserPassword(registerRequestDTO.getPassword()));
            user.setAddress("");
            user.setCity("");
            user.setPostalCode("");
            user.setPhoneNumber("");
            user.setDateCreated(Calendar.getInstance().toInstant());
            user.setDateUpdated(Calendar.getInstance().toInstant());
            user.setRegistrationConfirmed(true);
            user.setRole(Role.ROLE_USER);

//            if(registerRequestDTO.isNewsLetter()){
//                newsletterRepository.save(new Newsletter(registerRequestDTO.getEmail()));
//            }

            return userRepository.save(user);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_PASS_AND_REPEAT_PASS_NOT_SAME);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void createPassword(String password, String registrationToken) {
        if (!validatePassword(password)) {
            throw new InvalidPasswordException();
        }

        Optional<User> user = userRepository.findOneByRegistrationToken(registrationToken);

        if (!user.isPresent()) {
            throw new TokenExpiredException();
        }

        Instant now = new Timestamp(System.currentTimeMillis()).toInstant();
        if (user.get().getRegistrationTokenExpirationDate().isBefore(now)) {
            throw new TokenExpiredException();
        }

        user.get().setPassword(encryptUserPassword(password));
        user.get().setRegistrationConfirmed(true);
        userRepository.save(user.get());
    }

    @Override
    public void sendRecoveryPasswordMail(String email) {
        User user = userRepository.findOneByEmailAndDeletedFalse(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, Constants.VALIDITY_OF_TOKEN_IN_DAYS);
        user.setResetTokenExpirationDate(c.getTime().toInstant());
        user.setResetToken(Util.generateUniqueString());

        userRepository.save(user);

        String urlPart = Constants.URL_PASSWORD_RESET;

        passwordRecoveryEmailService.sendEmail(
                deeplinkUrl,
                "?resetToken=" + user.getResetToken(),
                emailFrom,
                user.getEmail(),
                urlPart
        );
    }

    public void resetPassword(String password, String resetToken) {
        if (!validatePassword(password)) {
            throw new InvalidPasswordException();
        }

        Optional<User> user = userRepository.findOneByResetToken(resetToken);

        if (!user.isPresent()) {
            throw new TokenExpiredException();
        }

        Instant now = new Timestamp(System.currentTimeMillis()).toInstant();
        if (user.get().getResetTokenExpirationDate().isBefore(now)) {
            throw new TokenExpiredException();
        }

        user.get().setPassword(encryptUserPassword(password));
        user.get().setResetToken(null);
        user.get().setResetTokenExpirationDate(null);

        userRepository.save(user.get());
    }

    @Override
    public void changePassword(String email, String password) {
        User user = userRepository.findOneByEmailAndDeletedFalse(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        user.setPassword(encryptUserPassword(password));

        userRepository.save(user);
    }
}
