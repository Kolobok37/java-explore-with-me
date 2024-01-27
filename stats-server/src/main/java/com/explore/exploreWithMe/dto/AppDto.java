package com.explore.exploreWithMe.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDto {
    String uri;
    String app;
    Long hits;
}