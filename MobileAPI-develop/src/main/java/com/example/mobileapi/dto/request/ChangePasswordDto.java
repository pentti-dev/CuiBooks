package com.example.mobileapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
