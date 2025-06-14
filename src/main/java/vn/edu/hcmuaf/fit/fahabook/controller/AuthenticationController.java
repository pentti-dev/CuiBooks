package vn.edu.hcmuaf.fit.fahabook.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.RedirectView;
import vn.edu.hcmuaf.fit.fahabook.annotation.GetToken;
import vn.edu.hcmuaf.fit.fahabook.dto.request.CustomerRequestDTO;
import vn.edu.hcmuaf.fit.fahabook.dto.request.LoginRequest;
import vn.edu.hcmuaf.fit.fahabook.dto.response.ApiResponse;
import vn.edu.hcmuaf.fit.fahabook.dto.response.LoginResponse;
import vn.edu.hcmuaf.fit.fahabook.dto.validationgroup.ValidationGroups;
import vn.edu.hcmuaf.fit.fahabook.exception.AppException;
import vn.edu.hcmuaf.fit.fahabook.exception.ErrorCode;
import vn.edu.hcmuaf.fit.fahabook.service.AuthenticationService;
import vn.edu.hcmuaf.fit.fahabook.service.CustomerService;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Validated
public class AuthenticationController {

    AuthenticationService authenticationService;
    CustomerService customerService;

    @GetMapping("login-google")
    RedirectView redirectToGoogle() {
        return new RedirectView("/oauth2/authorize/google");
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws AppException {
        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .data(authenticationService.login(loginRequest))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@Parameter(hidden = true) @GetToken String token) {
        if (token == null || token.isEmpty()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        authenticationService.logout(token);
        return ApiResponse.success();
    }

    @PostMapping("/register")
    ApiResponse<Void> register(
            @RequestBody
            @Validated(ValidationGroups.Create.class) CustomerRequestDTO customer) throws AppException {

        customerService.saveCustomer(customer);
        return ApiResponse.success();
    }

    @PostMapping("/check-username")
    ApiResponse<Boolean> checkUsername(@RequestBody String username) {
        return ApiResponse.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .data(customerService.checkUsername(username))
                .build();
    }

    @PostMapping("/check-email")
    ApiResponse<Boolean> checkEmail(@RequestBody String email) {
        return ApiResponse.<Boolean>builder()
                .code(HttpStatus.OK.value())
                .data(customerService.checkEmail(email))
                .build();
    }

    @PostMapping("/checkTokenExpiration/{token}")
    ApiResponse<Void> checkTokenExpiration(@PathVariable("token") String token) throws AppException {
        authenticationService.checkTokenExpiration(token);
        return ApiResponse.success();
    }
}
