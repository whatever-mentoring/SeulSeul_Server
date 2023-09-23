package com.seulseul.seulseul.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private UUID uuid;
    private String token;
}
