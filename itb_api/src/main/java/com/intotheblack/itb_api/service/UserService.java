package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.LoginRequestDTO;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.PlayersResponseDTO;
import com.intotheblack.itb_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // METHODS:
    public User findUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public PlayersResponseDTO findUserPlayersByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new PlayersResponseDTO(user.getPlayers());
        }
        return new PlayersResponseDTO();
    }

    public boolean changePasswordWithUsername(String username, PasswordRequestDTO request) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (request == null || request.getOldPassword() == null || request.getNewPassword() == null) {
            throw new IllegalArgumentException("Invalid password request");
        }

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

    public boolean checkPasswordWithUsername(LoginRequestDTO login) {
        if (login == null || 
        login.getUsername() == null || login.getUsername().isEmpty() || 
        login.getPassword() == null || login.getPassword().isEmpty()) {
            
            throw new IllegalArgumentException("Invalid login request");
        }
        
        try {
            Optional<User> userOptional = userRepository.findByUsername(login.getUsername());
    
            if (userOptional.isEmpty()) {
                return false; // Usuario no encontrado
            }
    
            User user = userOptional.get();
            return checkPasswordWithUser(user, login.getPassword());
    
        } catch (NumberFormatException e) {
            return false; // ID no válido
        }
    }

    private boolean checkPasswordWithUser(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean deleteUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        
        Optional<User> userOptional = userRepository.findByUsername(username);
    
        if (userOptional.isPresent()) {
            userRepository.deleteById(userOptional.get().getId());
            return true; // Eliminado con éxito
        }
    
        return false; // Usuario no encontrado
    }
}
