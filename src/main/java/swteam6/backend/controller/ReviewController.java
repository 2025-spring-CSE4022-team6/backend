package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.request.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.dto.response.ReviewDetailDto;
import swteam6.backend.dto.response.ApiResponse;
import swteam6.backend.entity.Review;
import swteam6.backend.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //[POST] 리뷰 작성
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponseDto>> createReview(@RequestBody ReviewCreateDto requestDto) {
        ReviewResponseDto responseDto = reviewService.createReview(requestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, 200, "리뷰 작성 성공", responseDto));
    }

    //[GET] 리뷰 상세 조회
    @GetMapping
    public ApiResponse<List<ReviewDetailDto>> getReviewDetails(
            @RequestParam Long userId,
            @RequestParam Long placeId) {

        List<Review> reviews = reviewService.findAllByUserAndPlace(userId, placeId);

        List<ReviewDetailDto> dtoList = reviews.stream()
                .map(ReviewDetailDto::new)
                .collect(Collectors.toList());

        return new ApiResponse<>(true, 200, "리뷰 상세 조회 성공", dtoList);
    }
}
