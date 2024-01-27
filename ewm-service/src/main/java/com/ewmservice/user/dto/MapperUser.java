package com.ewmservice.user.dto;

import com.ewmservice.user.User;

public class MapperUser {
    public static UserShortDto mapToUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
