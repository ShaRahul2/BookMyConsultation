package org.upgrad.userservice.model.mapper;

import org.joda.time.DateTime;
import org.upgrad.userservice.model.dto.UserServiceDto;
import org.upgrad.userservice.model.entity.UserInfoEntity;

public class UserMapper {
    public static UserInfoEntity convertDTOToEntity(UserServiceDto userServiceDto) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setFirstName(userServiceDto.getFirstName());
        userInfo.setLastName(userServiceDto.getLastName());
        userInfo.setDob(userServiceDto.getDob());
        userInfo.setMobile(userServiceDto.getMobile());
        userInfo.setEmailId(userServiceDto.getEmailId());
        userInfo.setCreatedDate(userServiceDto.getCreatedDate());
        return userInfo;
    }
}
