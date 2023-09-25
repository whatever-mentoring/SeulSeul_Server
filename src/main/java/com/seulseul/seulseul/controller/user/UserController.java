package com.seulseul.seulseul.controller.user;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.user.UserDto;
import com.seulseul.seulseul.dto.user.UserOnlyDto;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user")
    public ResponseEntity<ResponseData> saveUser(@RequestBody UserOnlyDto userDto) {
        UserOnlyDto user = userService.saveUser(userDto);
        ResponseData responseData = new ResponseData(200, user);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
