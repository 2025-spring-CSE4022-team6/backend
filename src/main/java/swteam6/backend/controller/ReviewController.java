package swteam6.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swteam6.backend.dto.response.ReviewDetailResponse;
import swteam6.backend.entity.Review;
import swteam6.backend.service.ReviewService;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //[POST] 리뷰 작성

    //[GET] 리뷰 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailResponse> getReviewDetail(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        ReviewDetailResponse response = new ReviewDetailResponse(review);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
