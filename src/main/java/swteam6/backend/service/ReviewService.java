package swteam6.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import swteam6.backend.dto.request.ReviewCreateDto;
import swteam6.backend.dto.response.ReviewResponseDto;
import swteam6.backend.entity.*;
import swteam6.backend.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    //리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(ReviewCreateDto requestDto) {
        Place place = placeRepository.findById(requestDto.getPlaceId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "장소를 찾을 수 없습니다: " + requestDto.getPlaceId()
                ));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "사용자를 찾을 수 없습니다: " + requestDto.getUserId()
                ));

        Review review = new Review(
                user,
                place,
                requestDto.getTitle(),
                requestDto.getComment(),
                requestDto.getScore()
        );

        Review saved = reviewRepository.save(review);
        return ReviewResponseDto.fromEntity(saved);
    }
    //리뷰 상세 조회
    @Transactional(readOnly = true)
    public List<Review> findAllByUserAndPlace(Long userId, Long placeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다: " + userId
                ));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다: " + placeId
                ));

        List<Review> reviews = reviewRepository.findByUserAndPlaceOrderByIdDesc(user, place);
        if (reviews.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("리뷰를 찾을 수 없습니다: userId=%d, placeId=%d", userId, placeId)
            );
        }
        return reviews;
    }
}
