package com.main.app.config;

import com.main.app.domain.model.user.User;
import com.main.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.main.app.static_data.Messages.USERNAME_NOT_FOUND;

/**
 * The custom implementation of the user details service, providing
 * user data from the system database for authorization.
 *
 * @author Nikola
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findOneByEmailAndDeletedFalse(username);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
        }

        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        String password = user.get().getPassword();

        builder.password(password);
        builder.authorities(user.get().getRole().toString());

        return builder.build();
    }
}
