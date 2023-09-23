package com.woolie.chat.ui;

import com.woolie.chat.domain.Chat;
import com.woolie.chat.infrastructure.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class MessageSendController {
    private final ChatRepository chatRepository;

    @MessageMapping("/chat")
    public void sendMessage(){

    }

    @GetMapping("/multiRoom/home")
    public String homeController(Model model, HttpServletRequest request) {
        Collection<Chat> chatRooms = chatRepository.findAll();

        model.addAttribute("collection", chatRooms);
        return "home";
    }

    @GetMapping("/multiRoom/room")
    public String roomController(Model model, HttpServletRequest request) {
        String roomId = request.getParameter("id");

        model.addAttribute("roomId", roomId);
        return "room";
    }
}
