package com.seulseul.seulseul.service.user;

import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.dto.firebase.FCMDto;
import com.seulseul.seulseul.dto.user.UserDto;
import com.seulseul.seulseul.dto.user.UserOnlyDto;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public UserOnlyDto saveUser(UserOnlyDto userOnlyDto) {
        userRepository.save(new User(userOnlyDto.getUuid()));
        return userOnlyDto;
    }

    public User getUserByUuid(UUID uuid) {
        User user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Transactional
    public void saveToken(User user, FCMDto fcmDto) {
        UserDto userDto = new UserDto();
//        userDto.setUuid(user.getUuid());
        userDto.setToken(fcmDto.getToken());
//        userRepository.saveAndFlush(new User(userDto.getUuid(), userDto.getToken()));
        user.updateToken(userDto.getToken());
    }
}
