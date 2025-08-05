package example.auth_service;

import example.auth_service.data.DataClasses.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/user/api/create")
    void createUser(@RequestBody UserDTO userDto);

    @GetMapping("/user/api/email/{email}")
    UserDTO getUserByEmail(@PathVariable String email);
}


