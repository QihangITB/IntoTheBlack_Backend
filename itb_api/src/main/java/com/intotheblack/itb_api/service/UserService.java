package com.intotheblack.itb_api.service;

import com.intotheblack.itb_api.model.User;
import com.intotheblack.itb_api.dto.PasswordRequestDTO;
import com.intotheblack.itb_api.dto.PlayersResponseDTO;
import com.intotheblack.itb_api.dto.UserRegisterDTO;
import com.intotheblack.itb_api.dto.UserLoginDTO;
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
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
    }

    public PlayersResponseDTO findPlayersByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return new PlayersResponseDTO(user.getPlayers());
        }
        return new PlayersResponseDTO();
    }

    public User registerUser(UserRegisterDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());        
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        return userRepository.save(user);
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

    public boolean checkPasswordWithUsername(UserLoginDTO login) {
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
        Optional<User> userOptional = userRepository.findByUsername(username);
    
        if (userOptional.isPresent()) {
            userRepository.deleteById(userOptional.get().getId());
            return true; // Eliminado con éxito
        }
    
        return false; // Usuario no encontrado
    }
}
