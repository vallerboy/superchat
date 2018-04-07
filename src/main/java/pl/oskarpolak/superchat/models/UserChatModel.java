package pl.oskarpolak.superchat.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Data
public class UserChatModel {
    private WebSocketSession session;
    private String nickname;

    public UserChatModel(WebSocketSession session){
        this.session = session;
    }

    public void sendMessage(String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }
}
