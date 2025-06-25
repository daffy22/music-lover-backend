package com.app.musiclover.api.dto;

import com.app.musiclover.data.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String email;

    public UserResponse(UserEntity userEntity) {
        BeanUtils.copyProperties(userEntity, this);
    }
}
