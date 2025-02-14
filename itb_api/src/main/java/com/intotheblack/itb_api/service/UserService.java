import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Método para crear un nuevo usuario con una contraseña encriptada
    public User createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());  // Encripta la contraseña
        user.setPassword(encryptedPassword);  // Establece la contraseña encriptada

        // Guarda el usuario en la base de datos (suponiendo que tienes un repositorio)
        return userRepository.save(user);
    }

    // Método para verificar la contraseña del usuario (cuando inician sesión)
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
