package com.sogeti.filmland.security;

import com.sogeti.filmland.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsernameSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UsernameSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.sogeti.filmland.user.User> user = userRepository.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            return User.withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .authorities(user.get().getRole().toString()).build();
        }  else {
            throw new UsernameNotFoundException("user not found: " + email);
        }
    }
}
