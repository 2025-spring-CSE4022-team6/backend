package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    //[POST] 회원가입
    @PostMapping("/signup")
    public ApiResponse<UserResponseDto> signup(@RequestBody UserSignupDto signupDto) {
        UserResponseDto responseDto = userService.signup(signupDto);
        return new ApiResponse<>(true, 200, "회원가입 성공", responseDto);
    }
}
