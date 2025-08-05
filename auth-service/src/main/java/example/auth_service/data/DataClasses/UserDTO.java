package example.auth_service.data.DataClasses;

import lombok.Data;

@Data
public class UserDTO {

    Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}