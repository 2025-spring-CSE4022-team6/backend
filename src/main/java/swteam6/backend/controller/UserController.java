package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swteam6.backend.security.JwtTokenProvider;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.dto.response.ProfileResponse;
import swteam6.backend.service.UserService;
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://13.124.170.215:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        ProfileResponse response = userService.getUserProfile(email);
        return ResponseEntity.ok(new ApiResponse(true, 200, "마이페이지 조회 성공", response));
    }

}
