package com.ewmservice.dto.dtoStats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitDto {
    private String name;
    private String uri;
    private String ip;
    private String timestamp;

}
