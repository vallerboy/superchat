package pl.oskarpolak.superchat.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@NoArgsConstructor
public class UserChatModel {
    private WebSocketSession session;
    private String nickname;

    public UserChatModel(WebSocketSession session){
        this.session = session;
    }
}
