package example.auth_service.data.DataClasses;


import lombok.Data;

@Data
public class LoginDto {
        private String email;
        private String password;
}
