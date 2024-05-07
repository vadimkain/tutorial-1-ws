package com.example.ws1.ws;

import com.example.ws1.dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    //    Mapped as /app/application
//    У нас есть контроллер сообщений который определяет что во первых любое сообщение поступающее /app/application мы будем обрабатывать
//    Внутренне это означает что любой клиент отправляющий сообщение в /app/application будет обработан в контроллере.
//    Так что это неявно вытекает в то что мы определили в конфигурации где указали префикс
//    Теперь эта штука выполняет функцию клиента которая отправляет сообщение по этому конкретному адресу или уведомление по этому конкретному получателю
    @MessageMapping("/application")
//    А за тем перенаправляет его обратно ко всем сообщением /all/messages
//    Это означает что все клиенты которые подписались на это конкретное направление начнут получать именно это уведомление
    @SendTo("/all/messages")
    public Message send(final Message message) throws Exception {
        return message;
    }

    //    Mapped as /app/private
//    Мы хотим обработать сообщение таким образом чтобы оно было отправлено только тому пользователю которому я пытаюсь его отправить
//    Любой клиент отправляющий уведомление на /app/private будет обработан этой конкретной функцией
//    Теперь у сообщения который приходит есть два параметра: сообщение и кому.
    @MessageMapping("/private")
    public void sendSpecificUser(@Payload Message message) {
//        Эти две части это по сути сообщения которые я хочу отправить. Это ничто иное что пользовательский класс
//        отправляем это конкретное сообщение которое приходит ко мне от внешнего клиента - отправляем его к конкретному адресату.
//        на самом деле не указываем здесь ни идентификатор сеанса, ни даже не пользователя. Именно это обрабатывает конкретный шаблон
//        поскольку мы используем convertAndSendToUser - он берёт этого конкретного пользователя, определяет что это
//        за сеанс и за тем добавляет его сюда во внутрь вместе с ним зная что он должен отправить его по пользовательскому пути
//        Значит он отправляет конкретному пользователю идентификатору сеанса
        messagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
