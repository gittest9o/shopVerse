package example.user_service.controllers;


import example.user_service.client.order.OrderClient;
import example.user_service.client.order.OrderDto;
import example.user_service.entity.User;
import example.user_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserRepository userRepository;
    private final OrderClient orderClient;


    @GetMapping
    public String userPage(Model model, @RequestHeader("X-User-Id") Long userId) {
        //TODO if user = null

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        List<OrderDto> orders = orderClient.findOrderByUserId(userId);


        BigDecimal totalSpent = orders.stream()
                .map(OrderDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("totalSpent", totalSpent);


        return "user";
    }
}
