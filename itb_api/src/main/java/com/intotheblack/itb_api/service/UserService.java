package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.UserDTO;
import com.intotheblack.itb_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // METHODS:

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
    }

    public User registerUser(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());        
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());

        return userRepository.save(newUser);
    }

    public boolean changePasswordWithUsername(String username, PasswordRequestDTO request) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(username);
    
            if (userOptional.isEmpty()) {
                return false; // Usuario no encontrado
            }
    
            User user = userOptional.get();
            String oldP = request.getOldPassword();
            String newP = request.getNewPassword();
            
            if (!checkPasswordWithUser(user, oldP)) {
                return false; // Contraseña antigua incorrecta
            }
    
            if (newP == null || newP.isBlank() || newP.equals(oldP)) {
                return false; // Nueva contraseña vacía o igual a la anterior
            }
    
            user.setPassword(passwordEncoder.encode(newP));
            userRepository.save(user);
            return true;
    
        } catch (NumberFormatException e) {
            return false; // ID no válido
        }
    }

    public boolean checkPasswordWithUser(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean checkPasswordWithUsername(String username, String password) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(username);
    
            if (userOptional.isEmpty()) {
                return false; // Usuario no encontrado
            }
    
            User user = userOptional.get();
            return checkPasswordWithUser(user, password);
    
        } catch (NumberFormatException e) {
            return false; // ID no válido
        }
    }

    public boolean deleteUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
    
        if (userOptional.isPresent()) {
            userRepository.deleteById(userOptional.get().getId());
            return true; // Eliminado con éxito
        }
    
        return false; // Usuario no encontrado
    }
}
