package com.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class UserShortDto {
    Integer id;
    String name;
}
