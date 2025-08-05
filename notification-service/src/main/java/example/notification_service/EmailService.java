package example.notification_service;

import example.order_service.data.dataClases.CartItem;
import example.order_service.data.dataClases.Notification;
import example.order_service.data.dataClases.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void handleNotify(Notification notification) {
       sendSimpleMessage(notification);
    }

    public void sendSimpleMessage(Notification notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("antkatorets@gmail.com");
        message.setTo(notification.getEmail());
        message.setSubject("Заказ #" + notification.getOrderId() + " оформлен");
        message.setText(buildOrderMessage(notification.getOrderInfo()));
        emailSender.send(message);
    }

    private String buildOrderMessage(OrderInfo info) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ваш заказ принят!\n\n");
        sb.append("Состав заказа:\n");
        for (CartItem item : info.getItems()) {
            sb.append("- ").append(item.getName())
                    .append(" x").append(item.getQuantity())
                    .append(" — ").append(item.getPrice()).append("$\n");
        }
        sb.append("\nОбщая сумма: ").append(info.getTotalPrice()).append("$\n");
        return sb.toString();
    }
}

