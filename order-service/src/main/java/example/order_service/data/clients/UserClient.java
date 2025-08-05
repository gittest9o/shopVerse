package example.order_service.data.clients;


import example.order_service.data.dataClases.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/user/api/id/{id}")
    UserDto getUser(@PathVariable Long id);
}

