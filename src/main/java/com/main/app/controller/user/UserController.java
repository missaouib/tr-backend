package com.main.app.controller.user;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.user.*;
import com.main.app.domain.model.user.User;
import com.main.app.service.user.CurrentUserService;
import com.main.app.service.user.UserService;
import com.main.app.service.email.SubmitContactEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.main.app.converter.user.UserConverter.userDTOtoUserEntity;
import static com.main.app.converter.user.UserConverter.userEntityToUserDTO;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    private final CurrentUserService currentUserService;

    private final SubmitContactEmailService submitContactEmailService;


    @GetMapping(path = "/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(currentUserService.getCurrentUserDTO(), HttpStatus.OK);
    }

    @PutMapping(path = "/current")
    public ResponseEntity<UserDTO> updateCurrentUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(userEntityToUserDTO(currentUserService.updateCurrentUser(userDTO)));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> edit(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return new ResponseEntity<>(userEntityToUserDTO(userService.edit(userDTOtoUserEntity(userDTO), id)), HttpStatus.OK);
    }

    @PutMapping(path = "/createPassword/{registrationToken}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createPassword(@PathVariable String registrationToken, @RequestBody PasswordDTO passwordDTO) {
        userService.createPassword(passwordDTO.password, registrationToken);
    }

    @PostMapping(value = "/password/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordRecovery(@RequestBody EmailDTO emailDTO) {
        userService.sendRecoveryPasswordMail(emailDTO.getEmail());
    }

    @PutMapping("/resetPassword/{resetToken}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveNewPassword(@PathVariable String resetToken, @RequestBody PasswordDTO passwordDTO) {
        userService.resetPassword(passwordDTO.password, resetToken);
    }

    @PostMapping(path = "/update-password")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        currentUserService.updatePassword(passwordUpdateDTO);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return new ResponseEntity<>(userEntityToUserDTO(userService.register(registerRequestDTO)), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities<User>> getAllBySearchParam(Pageable pageable, @RequestParam(name = "searchParam") String searchParam) {
        return new ResponseEntity<>(userService.getAllBySearchParam(searchParam, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userEntityToUserDTO(userService.getOne(id)), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> add(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.adminUserSave(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userEntityToUserDTO(userService.delete(id)), HttpStatus.OK);
    }

    @PostMapping(path = "/submitContact")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submitContact(@RequestBody ContactDTO  contactDTO) {
        submitContactEmailService.sendEmail(contactDTO.getName(), contactDTO.getEmail(), contactDTO.getCity(), contactDTO.getSubject(), contactDTO.getMessage());
    }
}
