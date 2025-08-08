package com.shop.notification.service;


import com.shop.order.service.data.dataClases.CartItem;
import com.shop.order.service.data.dataClases.Notification;
import com.shop.order.service.data.dataClases.OrderInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailService emailService;


    @Test
    void sendSimpleMessage_shouldSendCorrectEmail() {

        CartItem item1 = new CartItem();
        item1.setName("Item A");
        item1.setQuantity((short) 2);
        item1.setPrice(new BigDecimal("15.00"));

        CartItem item2 = new CartItem();
        item2.setName("Item B");
        item2.setQuantity((short) 1);
        item2.setPrice(new BigDecimal("25.00"));

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setItems(List.of(item1, item2));
        orderInfo.setTotalPrice(new BigDecimal("55.00"));

        Notification notification = new Notification();
        notification.setEmail("test@example.com");
        notification.setOrderId(123L);
        notification.setOrderInfo(orderInfo);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendSimpleMessage(notification);


        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals("test@example.com", sentMessage.getTo()[0]);
        assertEquals("Заказ #123 оформлен", sentMessage.getSubject());
        assertTrue(sentMessage.getText().contains("Item A x2"));
        assertTrue(sentMessage.getText().contains("Item B x1"));
        assertTrue(sentMessage.getText().contains("Общая сумма: 55.00$"));
    }
}