package com.ewmservice.model.auxiliaryEntities;

import com.ewmservice.dto.dto.AppDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class AppDtoList {
    private List<AppDto> list;

    public AppDtoList() {
        list = new ArrayList<>();
    }
}
