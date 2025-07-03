package com.scaler.userservicejuly.Dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;

}
