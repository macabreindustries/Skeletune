package com.teamllaj.skeletune.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String username;
    private String messageContent;

    // Campo de fecha formateada (igual que en NotificationDTO)
    private String duration;
}