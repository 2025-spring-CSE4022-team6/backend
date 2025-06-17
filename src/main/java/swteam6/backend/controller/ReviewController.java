package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.request.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.dto.response.ReviewDetailDto;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.entity.Review;
import swteam6.backend.service.ReviewService;
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "http://13.124.170.215:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //[POST] 리뷰 작성
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(@PathVariable("id") Long id, @RequestBody ReviewCreateDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        ReviewResponseDto responseDto = reviewService.createReview(id,email,requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, 200, "리뷰 작성 성공", responseDto));
    }

    //[GET] 리뷰 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDetailDto>> getReviewDetail(@PathVariable Long id) {
        ReviewDetailDto response = reviewService.getReviewDetail(id);
        return ResponseEntity.ok(new ApiResponse<>(true, 200, "리뷰 상세 조회 성공", response));
    }
}
