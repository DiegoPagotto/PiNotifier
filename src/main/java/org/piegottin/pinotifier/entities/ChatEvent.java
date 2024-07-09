package org.piegottin.pinotifier.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.piegottin.pinotifier.enums.ChatAction;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ChatEvent {
    private UUID playerId;
    private Boolean awaitingMessage;
    private ChatAction chatAction;
}
