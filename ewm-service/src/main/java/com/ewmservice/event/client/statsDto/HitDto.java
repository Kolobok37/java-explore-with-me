package com.ewmservice.event.client.statsDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitDto {
    String name;
    String uri;
    String ip;
    String timestamp;

}
