package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.response.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.dto.response.ReviewDetailDto;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.entity.Review;
import swteam6.backend.service.ReviewService;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //[POST] 리뷰 작성
    @PostMapping
    public ApiResponse<ReviewResponseDto> createReview(@RequestBody ReviewCreateDto requestDto) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        return new ApiResponse<>(true, 201, "리뷰 작성 성공", responseDto);
    }

    //[GET] 리뷰 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<ReviewDetailDto> getReviewDetail(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        ReviewDetailDto response = new ReviewDetailDto(review);
        return new ApiResponse<>(true, 200, "리뷰 상세 조회 성공", response);
    }
}
