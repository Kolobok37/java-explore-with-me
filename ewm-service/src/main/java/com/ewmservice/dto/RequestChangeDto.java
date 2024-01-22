package com.ewmservice.dto;

import com.ewmservice.model.auxiliaryEntities.StatusRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestChangeDto {
    List<Integer> requestIds;
    String status;
}
