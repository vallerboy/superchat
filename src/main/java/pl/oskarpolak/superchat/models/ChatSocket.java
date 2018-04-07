package pl.oskarpolak.superchat.models;

import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableWebSocket
@Component
public class ChatSocket extends TextWebSocketHandler implements WebSocketConfigurer {

    List<UserChatModel> userList = new ArrayList<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(this, "/chat")
                .setAllowedOrigins("*");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userList.add(new UserChatModel(session));

        UserChatModel userChatModel = findUserBySessionId(session);
        userChatModel.sendMessage("Witaj na naszym chacie!");
        userChatModel.sendMessage("Twoja pierwsza wiadomość będzie Twoim nickiem");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        userList.remove(findUserBySessionId(session));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        UserChatModel userChatModel = findUserBySessionId(session);

        if(userChatModel.getNickname() == null){
            userChatModel.setNickname(message.getPayload());
            userChatModel.sendMessage("Ustawiono Twój nick!");
            return;
        }

        sendMessageToAll(message);
    }

    private void sendMessageToAll(TextMessage message) throws IOException {
        for (UserChatModel userModel : userList) {
             userModel.sendMessage(message.getPayload());
        }
    }

    private UserChatModel findUserBySessionId(WebSocketSession session){
        return userList.stream()
                .filter(s -> s.getSession().getId().equals(session.getId()))
                .findAny().get();
    }


}
