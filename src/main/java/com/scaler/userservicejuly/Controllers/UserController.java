package com.scaler.userservicejuly.Controllers;

import com.scaler.userservicejuly.BusinessLogic.UserBL;
import com.scaler.userservicejuly.Dtos.*;
import com.scaler.userservicejuly.Dtos.ResponseStatus;
import com.scaler.userservicejuly.Models.Token;
import com.scaler.userservicejuly.Models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserBL userBL;

    public UserController(UserBL userBL){
        this.userBL = userBL;
    }
    //login, sign up, validateToken, logout

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto responseDto = new LoginResponseDto();

        try{
            Token token = userBL.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());

            responseDto.setToken(token.getValue());
            responseDto.setStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
        }
        return  responseDto;
    }
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto){

        User user = userBL.signup(signupRequestDto.getEmail(),signupRequestDto.getPassword(),signupRequestDto.getName());
        UserDto userDto = UserDto.from(user);
        return userDto;
    }
    @PatchMapping("/logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto){
        userBL.logout(logoutRequestDto.getToken());
    }

    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable String token){
        User user = userBL.validateToken(token);
        return UserDto.from(user);
    }


}
