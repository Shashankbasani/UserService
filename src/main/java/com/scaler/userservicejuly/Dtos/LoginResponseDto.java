package com.scaler.userservicejuly.Dtos;

import com.scaler.userservicejuly.Models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private ResponseStatus status;
}
