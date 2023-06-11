package com.coupons.authservice.messaging.producer;

import com.coupons.authservice.model.User;
import com.coupons.authservice.model.event.UserDeletedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserEventProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendUserDeletedEvent(User user) {
        Set<String> userRoles = user.getRoles()
                .stream()
                .map(role -> role
                        .getName()
                        .name())
                .collect(Collectors.toSet());
        UserDeletedEvent event = new UserDeletedEvent(user.getId(), userRoles);
        sendMessage("user-deleted", event);
    }
    private void sendMessage(String routingKey, Object event){
        try {
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
