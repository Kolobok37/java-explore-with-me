package com.ewmservice.dto;

import com.ewmservice.model.Event;
import com.ewmservice.model.User;
import com.ewmservice.model.auxiliaryEntities.StatusRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestDto {
    Integer id;
    Integer requester;
    Integer event;
    LocalDateTime created;
    String status;
}
