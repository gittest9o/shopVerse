package example.auth_service.data;

import example.auth_service.UserClient;
import example.auth_service.data.DataClasses.UserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserClient userClient;


    public boolean isUserExists(String email) {
        try {
            userClient.getUserByEmail(email);
           return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }

    public void register(UserDTO registrationDto) {
        try {
            userClient.getUserByEmail(registrationDto.getEmail());
            throw new DataIntegrityViolationException("Пользователь с таким email уже существует");
        } catch (FeignException.NotFound e) {

            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(registrationDto.getFirstName());
            userDTO.setLastName(registrationDto.getLastName());
            userDTO.setEmail(registrationDto.getEmail());
            userDTO.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

            userClient.createUser(userDTO);
        }
    }

    public UserDTO authenticate(String email, String rawPassword) {
        if(isUserExists(email)) {
            UserDTO userDTO = userClient.getUserByEmail(email);
            if (!passwordEncoder.matches(rawPassword, userDTO.getPassword())) {
                throw new RuntimeException("Неверный пароль");
            }

            return userDTO;

        }else {
            throw new RuntimeException("Пользователь не найден");
        }

    }
}