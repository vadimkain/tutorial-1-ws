package com.example.ws1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    //    Создаём и настраиваем внутреннего брокера который будет хранить сообщения и передавать их различным клиентам, для этого создаём два пункта назначения
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        Первый пункт назначения /all для всех и конкретный /specific
//        /all - транслируем всем клиентам которые были подключены к этому конкретному приложению
//        /specific - конкретное приложение будет использоваться для отправки уведомления только определённым пользователям
        config.enableSimpleBroker("/all", "/specific");
//        Префиксы назначения этого приложения. Устанавливаем /app.
//        По сути это похоже на то что любое сообщение отправленное на этот конкретный префикс будет отправлено на определенный контроллер
        config.setApplicationDestinationPrefixes("/app");
    }

    //    STOMP - надстройка над WS
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        регистрируем конечный ендпоинт на котором будем осуществлять связь с сокетом
        registry.addEndpoint("/ws");
//        также указываем на случай если где-то не сработает потому что некоторые браузеры не поддерживают веб-сокеты STOMP, возвращаемся к реализации SockJS
        registry.addEndpoint("/ws").withSockJS();
    }
}
