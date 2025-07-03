package com.scaler.userservicejuly.Dtos;

import com.scaler.userservicejuly.Models.Roles;
import com.scaler.userservicejuly.Models.User;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.List;

@Setter
@Getter
public class UserDto {
    private String name;
    private String email;
    private List<Roles> roleList;

    public static UserDto from(User user){

        if (user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoleList(user.getRoles());
        return userDto;

    }
}
