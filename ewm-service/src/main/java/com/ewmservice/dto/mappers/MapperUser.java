package com.ewmservice.dto.mappers;

import com.ewmservice.dto.UserShortDto;
import com.ewmservice.model.User;

public class MapperUser {
    public static UserShortDto mapToUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
