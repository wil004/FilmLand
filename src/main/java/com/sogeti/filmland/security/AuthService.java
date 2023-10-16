package com.sogeti.filmland.security;

import com.sogeti.filmland.category.Category;
import com.sogeti.filmland.category.CategoryRepository;
import com.sogeti.filmland.category.user.UserCategory;
import com.sogeti.filmland.category.user.UserCategoryRepository;
import com.sogeti.filmland.user.User;
import com.sogeti.filmland.user.UserDTO;
import com.sogeti.filmland.user.UserRepository;
import com.sogeti.filmland.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authManager;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final UserCategoryRepository userCategoryRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public AuthService(AuthenticationManager authManager, UserRepository userRepository, JwtService jwtService,
                       UserCategoryRepository userCategoryRepository, CategoryRepository categoryRepository) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userCategoryRepository = userCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public String signIn(UserDTO userDTO) throws AccountNotFoundException {
        Optional<User> user = userRepository.findByEmailIgnoreCase(userDTO.getEmail());

        if (user.isPresent()) {
            return authentication(userDTO);
        } else {
            throw new AccountNotFoundException("User not found");
        }
    }

    private String authentication(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        Authentication auth = authManager.authenticate(up);

        UserDetails ud = (UserDetails) auth.getPrincipal();

        return jwtService.generateToken(ud);
    }

    public int createUser(UserDTO userDTO) {
        if (userRepository.findByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists");
        }

        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPassword(password);
        user.setRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(user);
        saveNewUserAvailableCategories(savedUser);

        return savedUser.getId();
    }

    public void saveNewUserAvailableCategories(User user) {
        List<Category> categoryList = categoryRepository.findAll();
        List<UserCategory> availableContentList = new ArrayList<>();
        for(Category category : categoryList) {
            UserCategory availableUserCategory = new UserCategory();
            availableUserCategory.setUser(user);
            availableUserCategory.setCategory(category);
            availableContentList.add(availableUserCategory);
        }
        userCategoryRepository.saveAll(availableContentList);
    }
}
