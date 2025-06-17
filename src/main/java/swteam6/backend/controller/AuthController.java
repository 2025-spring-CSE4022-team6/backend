package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.request.LoginRequestDto;
import swteam6.backend.dto.response.LoginResponseDto;
import swteam6.backend.dto.request.UserSignupDto;
import swteam6.backend.dto.response.UserResponseDto;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.service.UserService;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://13.124.170.215:3000"}, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    //[POST] 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(@RequestBody UserSignupDto signupDto) {
        UserResponseDto responseDto = userService.signup(signupDto);
        return ResponseEntity.ok(new ApiResponse<>(true, 200, "회원가입 성공", responseDto));
    }

    //[POST] 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginDto) {
        LoginResponseDto responseDto = userService.login(loginDto);
        return ResponseEntity.ok(new ApiResponse<>(true, 200, "로그인 성공", responseDto));
    }
}
